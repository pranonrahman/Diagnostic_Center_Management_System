package net.therap.service;

import net.therap.model.User;
import net.therap.viewModel.RoleUpdateViewModel;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

/**
 * @author raian.rahman
 * @since 8/7/22
 */
@Service
public class RoleUpdateViewModelService {

    public RoleUpdateViewModel getRoleUpdateViewModel(User user) {

        RoleUpdateViewModel roleUpdateViewModel = new RoleUpdateViewModel();

        roleUpdateViewModel.setDoctor(nonNull(user.getDoctor()));
        roleUpdateViewModel.setAdmin(nonNull(user.getAdmin()));
        roleUpdateViewModel.setPatient(nonNull(user.getPatient()));
        roleUpdateViewModel.setReceptionist(nonNull(user.getReceptionist()));

        if (nonNull(user.getDoctor())) {
            roleUpdateViewModel.setFee(user.getDoctor().getFee());
        }

        return roleUpdateViewModel;
    }
}
