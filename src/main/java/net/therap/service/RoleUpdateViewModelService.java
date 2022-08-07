package net.therap.service;

import net.therap.model.Person;
import net.therap.viewModel.RoleUpdateViewModel;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

/**
 * @author raian.rahman
 * @since 8/7/22
 */
@Service
public class RoleUpdateViewModelService {

    public RoleUpdateViewModel getRoleUpdateViewModel(Person person) {

        RoleUpdateViewModel roleUpdateViewModel = new RoleUpdateViewModel();

        roleUpdateViewModel.setDoctor(nonNull(person.getDoctor()));
        roleUpdateViewModel.setAdmin(nonNull(person.getAdmin()));
        roleUpdateViewModel.setPatient(nonNull(person.getPatient()));
        roleUpdateViewModel.setReceptionist(nonNull(person.getReceptionist()));

        if (nonNull(person.getDoctor())) {
            roleUpdateViewModel.setFee(person.getDoctor().getFee());
        }

        return roleUpdateViewModel;
    }
}
