/*
 * Copyright (c) 2020. Michał Parzych
 * All rights reserved
 */

package assistant.Utils.converters;

import assistant.FXModels.AuthorFXModel;
import assistant.database.models.Author;

public class AuthorConverter {
    public static Author convertToAuthor(AuthorFXModel author) {
        Author model = new Author();
        model.setId(author.getId());
        model.setFistName(author.getFistName());
        model.setLastName(author.getLastName());
        model.setMiddleName(author.getMiddleName());
        model.setCountry(author.getCountry());
        return model;
    }

    public static AuthorFXModel convertToAuthorFXModel(Author author){
        AuthorFXModel model = new AuthorFXModel();
        model.setId(author.getId());
        model.setFistName(author.getFistName());
        model.setLastName(author.getLastName());
        model.setMiddleName(author.getMiddleName());
        model.setCountry(author.getCountry());
        return model;
    }
}
