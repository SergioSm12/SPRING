package com.bolsadeideas.spring.forms.app.editors;

import com.bolsadeideas.spring.forms.app.services.IRoleService;
import com.bolsadeideas.spring.forms.app.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class RolesEditor extends PropertyEditorSupport {
    @Autowired
    private IRoleService roleService;

    @Override
    public void setAsText(String idString) throws IllegalArgumentException {
        try {
            Integer id = Integer.parseInt(idString);
            setValue(roleService.obtenerporId(id));
        } catch (NumberFormatException e) {
            setValue(null);
        }

    }
}
