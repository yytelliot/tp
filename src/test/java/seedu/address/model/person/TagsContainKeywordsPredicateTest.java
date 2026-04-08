package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

class TagsContainKeywordsPredicateTest {
    @Test
    void test_singleKeywordMatch() {
        TagsContainKeywordsPredicate predicate =
            new TagsContainKeywordsPredicate(Collections.singletonList("friends"));
        Person person = new PersonBuilder().withTags("friends").build();
        assertTrue(predicate.test(person));
    }

    @Test
    void test_singleKeywordNoMatch() {
        TagsContainKeywordsPredicate predicate =
            new TagsContainKeywordsPredicate(Collections.singletonList("colleagues"));
        Person person = new PersonBuilder().withTags("friends").build();
        assertFalse(predicate.test(person));
    }

    @Test
    void test_multipleKeywordsAllMatch() {
        TagsContainKeywordsPredicate predicate =
            new TagsContainKeywordsPredicate(Arrays.asList("friends", "colleagues"));
        Person person = new PersonBuilder().withTags("friends", "colleagues").build();
        assertTrue(predicate.test(person));
    }

    @Test
    void test_singleKeywordPartialMatch_noMatch() {
        TagsContainKeywordsPredicate predicate =
            new TagsContainKeywordsPredicate(Collections.singletonList("math"));
        Person person = new PersonBuilder().withTags("mathematics").build();
        assertFalse(predicate.test(person));
    }

    @Test
    void test_singleKeywordCaseInsensitiveExactMatch() {
        TagsContainKeywordsPredicate predicate =
            new TagsContainKeywordsPredicate(Collections.singletonList("FrIeNdS"));
        Person person = new PersonBuilder().withTags("friends").build();
        assertTrue(predicate.test(person));
    }

    @Test
    void test_multipleKeywordsPartialMatch() {
        TagsContainKeywordsPredicate predicate =
            new TagsContainKeywordsPredicate(Arrays.asList("friends", "colleagues"));
        Person person = new PersonBuilder().withTags("friends").build();
        assertFalse(predicate.test(person));
    }
}
