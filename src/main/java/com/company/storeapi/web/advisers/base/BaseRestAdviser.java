package com.company.storeapi.web.advisers.base;

import com.company.storeapi.core.exceptions.base.PortalException;
import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.core.exceptions.persistence.PortalPersistenceException;
import com.company.storeapi.core.exceptions.service.DataCorruptedServiceException;
import com.company.storeapi.core.exceptions.service.DataNotFoundServiceException;
import com.company.storeapi.model.payload.base.GenericResponseEntity;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransactionException;
import org.hibernate.TransientPropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public abstract class BaseRestAdviser {

    @ExceptionHandler(value = {TransactionSystemException.class, TransactionException.class, org.springframework.transaction.TransactionException.class, ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<GenericResponseEntity> errorTransactionExceptionHandler(final Exception serviceException) {
        GenericResponseEntity vndErrors;
        if (serviceException instanceof DataIntegrityViolationException || serviceException instanceof ConstraintViolationException) {
            vndErrors = buildResponse(new ServiceException(LogRefServices.ERROR_PERSISTENCE, "Problemas de integridad referncial:  ", serviceException), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(vndErrors, HttpStatus.BAD_REQUEST);
        } else if (serviceException instanceof org.springframework.transaction.TransactionException || serviceException instanceof TransactionException) {
            Throwable exception = serviceException.getCause();
            if (exception instanceof RollbackException) {
                Throwable subCause = exception.getCause();
                if (subCause instanceof DataNotFoundPersistenceException) {
                    DataNotFoundPersistenceException dataNotFoundPersistenceException = (DataNotFoundPersistenceException) serviceException.getCause();
                    vndErrors = buildResponse(dataNotFoundPersistenceException, HttpStatus.NOT_FOUND);
                    return new ResponseEntity<>(vndErrors, HttpStatus.NOT_FOUND);
                } else if (subCause instanceof DataCorruptedPersistenceException) {
                    DataCorruptedPersistenceException dataCorruptedPersistenceException = (DataCorruptedPersistenceException) serviceException.getCause();
                    vndErrors = buildResponse(dataCorruptedPersistenceException, HttpStatus.CONFLICT);
                    return new ResponseEntity<>(vndErrors, HttpStatus.CONFLICT);
                } else if (subCause instanceof ConstraintViolationException) {
                    vndErrors = buildResponse(new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "Hay una propiedad que es obligatoria."), HttpStatus.BAD_REQUEST);
                    return new ResponseEntity<>(vndErrors, HttpStatus.BAD_REQUEST);

                } else if (subCause instanceof javax.validation.ConstraintViolationException) {
                    Set<ConstraintViolation<?>> violations = ((javax.validation.ConstraintViolationException) subCause).getConstraintViolations();
                    vndErrors = buildResponse(new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT,
                            "Hay una(s) propiedad(es) obligatoria(s) que no fueron enviadas. CAUSAS: ".concat(violations.stream()
                                    .map(constraintViolation -> constraintViolation.getPropertyPath().toString()).collect(Collectors.joining(" - Campo: ")))), HttpStatus.BAD_REQUEST);
                    return new ResponseEntity<>(vndErrors, HttpStatus.BAD_REQUEST);
                } else if (subCause instanceof DataIntegrityViolationException) {
                    vndErrors = buildResponse(new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "Problemas de integridad referencial al persistir o actualizar.", exception.getCause()), HttpStatus.CONFLICT);
                    return new ResponseEntity<>(vndErrors, HttpStatus.CONFLICT);
                } else if (subCause instanceof TransientPropertyValueException) {
                    TransientPropertyValueException transientPropertyValueException = ((TransientPropertyValueException) subCause);
                    vndErrors = buildResponse(new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "Error en la persistencia en casacada. Al parecer esta referenciando una entidad o relación inexistente. Causa: ".concat(transientPropertyValueException.getTransientEntityName()), subCause), HttpStatus.CONFLICT);
                    return new ResponseEntity<>(vndErrors, HttpStatus.CONFLICT);
                }
            }
        }
        vndErrors = buildResponse(new ServiceException(LogRefServices.LOG_REF_SERVICES, "Ha ocurrido un error durante la transacción. ", serviceException.getCause()), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(vndErrors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ServiceException.class})
    protected ResponseEntity<GenericResponseEntity> errorServiceExceptionHandler(final ServiceException serviceException) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        GenericResponseEntity response = buildResponse(serviceException, status);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler
    protected ResponseEntity<GenericResponseEntity> errorPortalPersistenceExceptionHandler(final PortalPersistenceException persistenceException) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        GenericResponseEntity response = buildResponse(persistenceException, status);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(DataCorruptedPersistenceException.class)
    public ResponseEntity<GenericResponseEntity> errorDataCorruptedPersistenceExceptionHandler(final DataCorruptedPersistenceException dataCorruptedPersistenceException) {
        GenericResponseEntity vndErrors;
        if (dataCorruptedPersistenceException.getCause() instanceof InvalidDataAccessApiUsageException) {
            Throwable subCause = dataCorruptedPersistenceException.getCause().getCause();
            if (subCause instanceof IllegalStateException) {
                if (subCause.getCause() instanceof TransientPropertyValueException) {
                    TransientPropertyValueException transientPropertyValueException = ((TransientPropertyValueException) subCause.getCause());
                    vndErrors = buildResponse(new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT,
                            "Error en la persistencia en casacada. Al parecer esta referenciando una entidad o relación inexistente. Causa: "
                                    .concat(transientPropertyValueException.getPropertyName()).concat(" - Verifique el fragmento de la petición que sea correcto."), transientPropertyValueException), HttpStatus.CONFLICT);
                    return new ResponseEntity<>(vndErrors, HttpStatus.CONFLICT);
                }
                vndErrors = buildResponse(new DataCorruptedPersistenceException(LogRefServices.LOG_REF_SERVICES, "Ha ocurrido un error en el motor de persistencia. Causa: ", subCause), HttpStatus.INTERNAL_SERVER_ERROR);
                return new ResponseEntity<>(vndErrors, HttpStatus.CONFLICT);
            }
        } else if (dataCorruptedPersistenceException.getCause() instanceof DataIntegrityViolationException) {
            Throwable cause = dataCorruptedPersistenceException.getCause();
            if (cause.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException constraintViolationException = (ConstraintViolationException) cause.getCause();
                vndErrors = buildResponse(new ServiceException(LogRefServices.ERROR_PERSISTENCE, "Problemas de integridad referencial:  ".concat(constraintViolationException.getConstraintName())), HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(vndErrors, HttpStatus.CONFLICT);
            }

            vndErrors = buildResponse(new ServiceException(LogRefServices.ERROR_PERSISTENCE, "Problemas de integridad referencial:  ", dataCorruptedPersistenceException), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(vndErrors, HttpStatus.BAD_REQUEST);
        }
        vndErrors = buildResponse(dataCorruptedPersistenceException, HttpStatus.CONFLICT);
        return new ResponseEntity<>(vndErrors, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataNotFoundPersistenceException.class)
    public ResponseEntity<GenericResponseEntity> errorDataNotFoundPersistenceExceptionHandler(final DataNotFoundPersistenceException dataNotFoundPersistenceException) {
        GenericResponseEntity vndErrors = buildResponse(dataNotFoundPersistenceException, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(vndErrors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {DataCorruptedServiceException.class, InvalidDefinitionException.class, HttpMessageNotReadableException.class, HttpMessageConversionException.class})
    public ResponseEntity<GenericResponseEntity> errorDataCorruptedServiceExceptionHandler(final Exception dataCorruptedServiceException) {
        GenericResponseEntity response = null;
        if (dataCorruptedServiceException instanceof DataCorruptedServiceException) {
            DataCorruptedServiceException exception = ((DataCorruptedServiceException) dataCorruptedServiceException);
            if (exception.getLogRefServices().equals(LogRefServices.ERROR_SAVE)) {
                response = buildResponse((DataCorruptedServiceException) dataCorruptedServiceException, HttpStatus.CONFLICT);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            } else {
                response = buildResponse((DataCorruptedServiceException) dataCorruptedServiceException, HttpStatus.BAD_REQUEST);
            }
        } else if (dataCorruptedServiceException instanceof InvalidDefinitionException || dataCorruptedServiceException instanceof HttpMessageConversionException) {
            response = buildResponse(new DataCorruptedServiceException(LogRefServices.ERROR_DATA_CORRUPT, "Problema en la deserialización del objeto con mensaje: "
                    .concat(dataCorruptedServiceException.getLocalizedMessage()), dataCorruptedServiceException.getCause()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<GenericResponseEntity> errorDataNotFoundServiceExceptionHandler(final DataNotFoundServiceException dataNotFoundServiceException) {
        GenericResponseEntity response;
        HttpStatus status = HttpStatus.NOT_FOUND;
       if (dataNotFoundServiceException.getLogRefServices().equals(LogRefServices.ERROR_DATA_NOT_FOUND)) {
            status = HttpStatus.NO_CONTENT;
       }
        response = buildResponse(dataNotFoundServiceException, status);

        return new ResponseEntity<>(response, status);
    }


    private GenericResponseEntity buildResponse(PortalException portalException, HttpStatus status) {
        return buildResponseException(portalException, status);
    }

    private GenericResponseEntity buildResponse(ServiceException serviceException, HttpStatus status) {
        return buildResponseException(serviceException, status);
    }

    private GenericResponseEntity buildResponse(SecurityException securityException, HttpStatus status) {
        return buildResponseException(securityException, status);
    }

    private GenericResponseEntity buildResponseException(Exception serviceException, HttpStatus status) {
        return GenericResponseEntity.newBuilder()
                .withStatus(status)
                .withErrorType(serviceException)
                .withDetailMessage(serviceException.getLocalizedMessage())
                .withRootException(serviceException)
                .buildResponse();
    }

}
