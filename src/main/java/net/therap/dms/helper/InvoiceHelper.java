package net.therap.dms.helper;

import net.therap.dms.command.InvoiceCmd;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import static java.util.Objects.isNull;
import static net.therap.dms.controller.invoice.InvoiceController.INVOICE_CMD;

/**
 * @author khandaker.maruf
 * @since 8/17/22
 */
@Component
public class InvoiceHelper {

    public boolean invoiceNotCreated(ModelMap model) {
        InvoiceCmd invoice = (InvoiceCmd) model.get(INVOICE_CMD);

        return isNull(invoice) || isNull(invoice.getPatient());
    }
}
