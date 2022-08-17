package net.therap.dms.helper;

import net.therap.dms.command.InvoiceCmd;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import static java.util.Objects.isNull;

/**
 * @author khandaker.maruf
 * @since 8/17/22
 */
@Component
public class InvoiceHelper {

    public static final String INVOICE_CMD = "invoice";

    public boolean invoiceNotCreated(ModelMap model) {
        InvoiceCmd invoice = (InvoiceCmd) model.get(INVOICE_CMD);

        return isNull(invoice) || isNull(invoice.getPatient());
    }
}
