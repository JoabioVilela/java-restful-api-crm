package com.joabio.crm.validation;

import com.joabio.crm.exception.InvalidPageException;
import com.joabio.crm.exception.InvalidPageSizeException;

public class ValidatePagination {

    // Valida o parâmetro 'page'
    public static void validatePage(int page) {
        if (page < 0) {
            throw new InvalidPageException("O parâmetro 'page' deve ser não negativo.");
        }
    }

    // Valida o parâmetro 'pageSize'
    public static void validatePageSize(int pageSize) {
        if (pageSize <= 0 || pageSize > 100) { // Exemplo de limite máximo de pageSize
            throw new InvalidPageSizeException("O parâmetro 'pageSize' deve ser maior que 0 e menor ou igual a 100.");
        }
    }
}
