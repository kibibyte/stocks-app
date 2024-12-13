package com.myapp.usecase.trade;

import com.myapp.exceptions.InvalidArgumentException

class TradesExceptions {
  companion object {
    fun invalidArgument(): InvalidArgumentException {
      val code = ErrorCodes.INVALID_ARGUMENT;

      return InvalidArgumentException(code.name, code.message);
    }
  }

  enum class ErrorCodes(val message: String) {
    INVALID_ARGUMENT("Invalid argument")
  }
}
