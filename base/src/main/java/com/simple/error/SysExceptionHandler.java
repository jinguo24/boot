package com.simple.error;


import com.fasterxml.jackson.core.JsonParseException;
import com.simple.common.rest.ResultData;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


/**
 * 异常通用处理类
 * @author chenkx
 * @date 2018-01-09.
 */
@RestControllerAdvice
public class SysExceptionHandler {

	private static Logger logger = LoggerFactory.getLogger(SysExceptionHandler.class);

	@ExceptionHandler(AuthorizationException.class)
	public ResultData handleAuth(AuthorizationException ex) {
		logger.error(AuthorizationException.class.getSimpleName() + ":", ex.getMessage());
		return new ResultData(ErrorCode.LOGIN_USER_OR_PWD_ERROR.getCode(),  ErrorCode.LOGIN_USER_OR_PWD_ERROR.getMessage(ex.getLocalizedMessage()));
	}

	@ExceptionHandler(LockedAccountException.class)
	public ResultData handleAuthUserLocked(LockedAccountException ex) {
		logger.error(AuthorizationException.class.getSimpleName() + ":", ex.getMessage());
		return new ResultData(ErrorCode.USER_IS_LOCKED.getCode(),  ErrorCode.LOGIN_USER_OR_PWD_ERROR.getMessage(ex.getLocalizedMessage()));
	}

	@ExceptionHandler(SystemException.class)
	public ResultData handleSystem(SystemException ex) {
		logger.error(ex.getErrorCode() + "", ex.getMessage());
		return new ResultData(ex.getErrorCode().getCode(), ex.getMessage());
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResultData handleParameter(MissingServletRequestParameterException ex) {
		logger.error("MissingServletRequestParameterException:", ex);
		return new ResultData(ErrorCode.PARAMETER_EMPTY_ERROR.getCode(), ErrorCode.PARAMETER_EMPTY_ERROR.getMessage(ex.getParameterName()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResultData handleArgValid(MethodArgumentNotValidException ex) {
		logger.error("MethodArgumentNotValidException:", ex);
		StringBuilder message = new StringBuilder();
		for(int i = 0; i<ex.getBindingResult().getFieldErrorCount(); i++){
			message.append(",").append(ex.getBindingResult().getFieldErrors().get(i).getDefaultMessage());
		}
		return new ResultData(ErrorCode.BEAN_EMPTY_PROPERTY_ERROR.getCode(), ErrorCode.BEAN_EMPTY_PROPERTY_ERROR.getMessage(message.substring(1)));
	}

	@ExceptionHandler(ServletRequestBindingException.class)
	public ResultData handleArgValid(ServletRequestBindingException ex) {
		logger.error("ServletRequestBindingException:", ex);
		return new ResultData(ErrorCode.BEAN_EMPTY_PROPERTY_ERROR.getCode(),ErrorCode.BEAN_EMPTY_PROPERTY_ERROR.getMessage(ex.getMessage()));
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ResultData handleRepeatSubmit(DuplicateKeyException ex) {
		logger.error("DuplicateKeyException:", ex);
		return new ResultData(ErrorCode.REPEAT_SUBMIT_EXCEPTION.getCode(),ErrorCode.REPEAT_SUBMIT_EXCEPTION.getMessage(ex.getMessage()));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResultData handleArgMismatch(MethodArgumentTypeMismatchException ex) {
		logger.error("MissingServletRequestParameterException:", ex);
		return new ResultData(ErrorCode.PARAMETER_TYPE_ERROR.getCode(), ErrorCode.PARAMETER_TYPE_ERROR.getMessage(ex.getName()));
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResultData handleMethodNotSupport(HttpRequestMethodNotSupportedException ex) {
		logger.error("HttpRequestMethodNotSupportedException:", ex);
		return new ResultData(ErrorCode.METHOD_NOT_SUPPORT.getCode(), ErrorCode.METHOD_NOT_SUPPORT.getMessage(ex.getMethod()));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResultData handleArgNotReadable(HttpMessageNotReadableException ex) {
		logger.error("HttpMessageNotReadableException:", ex);
		return new ResultData(ErrorCode.PARAMETER_ERROR.getCode(), ErrorCode.PARAMETER_ERROR.getMessage());
	}

	@ExceptionHandler(NullPointerException.class)
	public ResultData handleNull(NullPointerException ex) {
		logger.error("NullPointerException:", ex);
		return new ResultData(ErrorCode.NULLPOINTER_ERROR.getCode(), ErrorCode.NULLPOINTER_ERROR.getMessage());
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResultData handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
		logger.error("HttpMediaTypeNotSupportedException:", ex);
		return new ResultData(ErrorCode.MEDIATYPE_NOT_SUPPORT.getCode(), ErrorCode.MEDIATYPE_NOT_SUPPORT.getMessage(ex.getContentType().getType()));
	}

	@ExceptionHandler(ClassCastException.class)
	public ResultData handleClassCast(ClassCastException ex) {
		logger.error("ClassCastException:", ex);
		return new ResultData(ErrorCode.CLASSCAST_ERROR.getCode(), ErrorCode.CLASSCAST_ERROR.getMessage());
	}

	@ExceptionHandler(JsonParseException.class)
	public ResultData jsonParseException(Exception e){
		logger.error("HttpMessageNotReadableException:", e);
		return new ResultData(ErrorCode.EXTEND_JSON_ERROR.getCode(), ErrorCode.EXTEND_JSON_ERROR.getMessage());
	}

	@ExceptionHandler(NumberFormatException.class)
	public ResultData handleNumberFormat(NumberFormatException ex) {
		logger.error("NumberFormatException:", ex);
		return new ResultData(ErrorCode.NUMBER_FORMAT_ERROR.getCode(), ErrorCode.NUMBER_FORMAT_ERROR.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResultData illegalArgumentException(IllegalArgumentException ex) {
		logger.error("NumberFormatException:", ex);
		return new ResultData(ErrorCode.PARAMETER_ERROR.getCode(), ErrorCode.PARAMETER_ERROR.getMessage() + ": " + ex.getLocalizedMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResultData handleException(Exception ex) {
		logger.error(ErrorCode.SERVER_ERROR.getMessage() + "", ex);
		return new ResultData(ErrorCode.SERVER_ERROR.getCode(), ErrorCode.SERVER_ERROR.getMessage());
	}

}
