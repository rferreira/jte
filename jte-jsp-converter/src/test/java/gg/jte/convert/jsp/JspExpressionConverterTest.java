package gg.jte.convert.jsp;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JspExpressionConverterTest {

    @Test
    void noContent() {
        assertConversion("", "");
    }

    @Test
    void empty() {
        assertConversion("${empty foo}", "isEmpty(foo)");
    }

    @Test
    void notEmpty() {
        assertConversion("${not empty foo}", "!isEmpty(foo)");
    }

    @Test
    void notEmptyOr() {
        assertConversion("${not empty foo or true}", "!isEmpty(foo) || true");
    }

    @Test
    void notEmptyAnd() {
        assertConversion("${not empty foo and false}", "!isEmpty(foo) && false");
    }

    @Test
    void parenthesis() {
        assertConversion("${(foo or bar) and (x == 1)}", "(foo || bar) && (x == 1)");
    }

    @Test
    void parenthesis2() {
        assertConversion("${foo or (bar and (x == 1))}", "foo || (bar && (x == 1))");
    }

    @Test
    void methodCall() {
        assertConversion("${page.getDescription(locale)}", "page.getDescription(locale)");
    }

    @Test
    void methodCall2() {
        assertConversion("${page.getDescription(a, b, c)}", "page.getDescription(a, b, c)");
    }

    @Test
    void methodCall3() {
        assertConversion("${page.doStuff()}", "page.doStuff()");
    }

    @Test
    void eq() {
        assertConversion("${data.quantity eq 42}", "data.quantity == 42");
    }

    @Test
    void ne() {
        assertConversion("${data.quantity ne 42}", "data.quantity != 42");
    }

    @Test
    void gt() {
        assertConversion("${data.quantity gt 42}", "data.quantity > 42");
    }

    @Test
    void lt() {
        assertConversion("${data.quantity lt 42}", "data.quantity < 42");
    }

    @Test
    void ternary() {
        assertConversion("${data.quantity == 42 ? 'Yay' : 'Nay'}", "data.quantity == 42 ? \"Yay\" : \"Nay\"");
    }

    @Test
    void not() {
        assertConversion("${!true}", "!true");
    }

    @Test
    void eqEnum() {
        // TODO maybe we can use type information to make this better
        assertConversion("${data.quality eq 'Good'}", "data.quality == \"Good\"");
    }

    @Test
    void addition() {
        assertConversion("${x + 0.05}", "x + 0.05");
    }

    @Test
    void subtraction() {
        assertConversion("${x - 0.05}", "x - 0.05");
    }

    @Test
    void multiplication() {
        assertConversion("${x * 0.05}", "x * 0.05");
    }

    @Test
    void division() {
        assertConversion("${x / 0.05}", "x / 0.05");
    }

    @Test
    void modulo() {
        assertConversion("${x % 10}", "x % 10");
    }

    @Test
    void calculation() {
        assertConversion("${x * 10 / 23.0}", "(x * 10) / 23.0");
    }

    @Test
    void function() {
        assertConversion("${fn:toLowerCase(viewModel.title)}", "fn:toLowerCase(viewModel.title)");
    }

    @Test
    void function2() {
        assertConversion("${fn:replace(message, newLine, '<br/>')}", "fn:replace(message, newLine, \"<br/>\")");
    }

    private void assertConversion(String el, String java) {
        assertThat(new JspExpressionConverter(el).getJavaCode()).isEqualTo(java);
    }
}