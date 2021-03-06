package org.nil.gramana.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.nil.gramana.models.ArrayPermutation;
import org.nil.gramana.models.Permutation;
import org.nil.gramana.models.StringPermutation;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by n0ne on 24/09/16.
 */
public class Scrambler {

    private static Pattern PATTERN_SPECIFIED_LETTERS (final String letters) {
        final String pattern = "^[%s]+";

        return Pattern.compile(
                String.format(pattern, letters)
        );
    }

    public static SortedSet<Permutation> permute (String input, String inSep) {
        return permute(input, inSep, ArrayPermutation.OUT_SEP_EMPTY);
    }

    public static SortedSet<Permutation> permute (String input, String inSep, String outSep) {
        final String[] tokens = input.split(inSep);
        final SortedSet<Permutation> permutations = new TreeSet<>();

        for (List<String> permutation: CollectionUtils.<String>permutations(Arrays.asList(tokens))) {
            permutations.add(
                    new ArrayPermutation(
                            permutation.toArray(new String[permutation.size()]),
                            outSep
                    )
            );
        }

        return permutations;
    }

    /**
     * Find all permutations of {@code tokens} contained in the InputStream {@code is}.<br>
	 * The InputStream is assumed to represent a list of dictionary words (a dictionary)
	 * divided line by line or, alternatively, as produced by DictionaryManager.createDictionary() method.
     *
     * @param is InputStream to check permutations from.
     * @param tokens list of string pieces whose permutations are to be found in {@code file}.
     * @return a sorted set of permutations contained in {@code file}.
     */
    public static SortedSet<Permutation> findInFileIgnoreCase (final InputStream is, String[] tokens) {
        final SortedSet<Permutation> result = new TreeSet<>();

        final Scanner reader = new Scanner(is, "UTF-8");
        final String joinedTokens = Utils.join(tokens, "");
        String word;

        while (reader.hasNextLine()) {
        	word = reader.nextLine();

			if (word != null && isPermutationOfIgnoreCase(word, tokens, joinedTokens)) { // Unsure if to check against null values for word
				result.add(new StringPermutation(word));
			}
		}

		reader.close();

        return result;
    }

    private static boolean isPermutationOfIgnoreCase (String supposedPermutation, String tokens[], String joinedTokens) {
        if (joinedTokens.length() != supposedPermutation.length()) {
            return false;
        }

        for (String token: tokens) {
            if (StringUtils.containsIgnoreCase(supposedPermutation, token)) {
                supposedPermutation = StringUtils.replaceOnceIgnoreCase(supposedPermutation, token, "");
            } else {
                return false;
            }
        }

        return supposedPermutation.isEmpty();
    }

}
