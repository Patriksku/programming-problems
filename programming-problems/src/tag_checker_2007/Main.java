package tag_checker_2007;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Main {

    public static void main(String[] args) throws IOException {
        Main p = new Main();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Queue<String> q;

        String input = "";
        StringBuilder paragraphBuilder = new StringBuilder();

        //Queue

        String paragraph = "";

        // EOI Condition
        while(!("#".equals(input = br.readLine()))) {

            // If line does not end with '#', then we know that it takes place over more than one line.
            if (!(input.charAt(input.length()-1) == '#')) {
                paragraphBuilder.append(input);
                paragraphBuilder.append(" "); // appending an empty space for proper paragraphs consisting of several lines.

                // Else, we know that this line is final.
            } else {
                paragraphBuilder.append(input);
                paragraph = paragraphBuilder.toString();

                //Loop through paragraph, build tags with tagBuilder, eject successful tags into a queue + clean up tagBuilder.
                q = p.searchForTags(paragraph);

                //Validate tags + print output
                p.validateInput(q);
                /*while (!q.isEmpty()) {
                    System.out.println(q.remove());
                }*/
                paragraphBuilder.setLength(0);
            }
        }
    }

    // Returns a queue (FIFO structure) that contains correct tags in the order they where found in,
    // From the beginning of the paragraph to the end.
    public Queue<String> searchForTags(String paragraph) {
        boolean possibleTag = false;
        boolean hasCapital = false;
        StringBuilder tagBuilder = new StringBuilder();
        Queue<String> q = new LinkedList<>();
        int tagIndex = 0;

        for (int i = 0; i < paragraph.length(); i++) {

            if (!possibleTag) {
                if(paragraph.charAt(i) == '<') {
                    tagBuilder.append(paragraph.charAt(i));
                    tagIndex++;
                    possibleTag = true;
                }
            } else {
                if (tagIndex == 1) {
                    if (paragraph.charAt(i) == '/') {
                        tagBuilder.append(paragraph.charAt(i));
                        tagIndex++;
                    } else if (Character.isUpperCase(paragraph.charAt(i))) {
                        hasCapital = true;
                        tagBuilder.append(paragraph.charAt(i));
                        tagIndex++;
                    } else { //RESET --> as this char indicates that this is not a valid tag.
                        possibleTag = false;
                        hasCapital = false;
                        tagIndex = 0;
                        tagBuilder.setLength(0);
                    }
                } else if (tagIndex == 2) {
                    if (hasCapital) {
                        if (paragraph.charAt(i) == '>') {
                            tagBuilder.append(paragraph.charAt(i));
                            /*tagBuilder.append(" "); // extra space for splitting all the tags prior to queue-insertion.*/
                            q.add(tagBuilder.toString());
                        }
                        //RESET + clearing tagBuilder --> as we are now finished with the tag (either completed valid tag or simply removed as it did not follow tag-conventions)
                        possibleTag = false;
                        hasCapital = false;
                        tagIndex = 0;
                        tagBuilder.setLength(0);
                    } else { ////////////////
                        if (Character.isUpperCase(paragraph.charAt(i))) {
                            tagBuilder.append(paragraph.charAt(i));
                            hasCapital = true;
                            tagIndex++;
                        } else {
                            //RESET + clearing tagBuilder
                            possibleTag = false;
                            hasCapital = false;
                            tagIndex = 0;
                            tagBuilder.setLength(0);
                        }

                    }
                } else { //tagIndex == 3
                    if (paragraph.charAt(i) == '>') {
                        tagBuilder.append(paragraph.charAt(i));
                        q.add(tagBuilder.toString());
                        /*tagBuilder.append(" "); // extra space for splitting all the tags prior to queue-insertion.*/
                    }
                    //RESET + clearing tagBuilder --> as we are now finished with the tag (either completed valid tag or simply removed as it did not follow tag-conventions)
                    possibleTag = false;
                    hasCapital = false;
                    tagIndex = 0;
                    tagBuilder.setLength(0);
                }
            }
        }
        return q;
    }

    // Validates the input and prints the result.
    public void validateInput (Queue<String> q) {
        Stack<String> stack = new Stack<>();
        String currentQElement = "";

        while (!q.isEmpty()) {
            currentQElement = q.remove();

            // Element from q NOT ending-tag.
            if (currentQElement.charAt(1) != '/') {
                if (q.isEmpty()) {
                    System.out.println("Expected </" + currentQElement.charAt(1) + "> found #");
                }
                stack.add(currentQElement);
            }

            // If element from q IS ending-tag.
            if (currentQElement.charAt(1) == '/')
                if (!stack.isEmpty()) {
                    if (currentQElement.charAt(2) == stack.peek().charAt(1)) { // If tag from stack matches the q ending-tag
                        stack.pop();
                        if (q.isEmpty() && !stack.isEmpty()) { // Special case where there is a missing ending tag after the q is empty (last example in assignment input)
                            System.out.println("Expected </" + stack.peek().charAt(1) + "> found #");
                            break;
                        } else if (q.isEmpty()) { // When both the stack and the q is empty (success)
                            System.out.println("Correctly tagged paragraph");
                            break;
                        }
                    } else {
                        System.out.println("Expected </" + stack.peek().charAt(1) + "> found " + currentQElement);
                        break;
                    }
                } else {
                    System.out.println("Expected # found " + currentQElement);
                    break;
                }
        }
    }
}
