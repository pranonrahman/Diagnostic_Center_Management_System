package net.therap.editor;

import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.util.Objects.nonNull;

/**
 * @author raian.rahman
 * @since 8/4/22
 */
@Component
public class DateEditor extends PropertyEditorSupport {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public String getAsText() {
        Date date = (Date) getValue();
        if (nonNull(date)) {
            return dateFormat.format(date);
        }
        return null;
    }

    @Override
    public void setAsText(String text) {
        if (nonNull(text) && !text.isBlank()) {
            try {
                setValue(dateFormat.parse(text));
            } catch (ParseException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
