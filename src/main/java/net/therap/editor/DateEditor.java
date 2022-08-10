package net.therap.editor;

import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static java.util.Objects.nonNull;

/**
 * @author raian.rahman
 * @since 8/4/22
 */
@Component
public class DateEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        if (nonNull(text) && !text.isBlank()) {
            try {
                setValue(new SimpleDateFormat("yyyy-MM-dd").parse(text));
            } catch (ParseException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
