package de.geheimagentnr1.recipes_lib;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RecipesLibraryTest {

    @Test
    void modIdIsValid() {

        String modId = "recipes_lib";
        assertTrue( modId.matches( "[a-z][a-z0-9_]{1,63}" ) );
    }
}
