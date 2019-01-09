package cz.cvut.fel.cs.sin.converter;

import cz.cvut.fel.cs.sin.entity.Author;
import cz.cvut.fel.cs.sin.service.AuthorService;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;

@ManagedBean
@RequestScoped
public class AuthorValueConventer implements Converter {
	@Inject
	private AuthorService authorService;

	@Override
	public Author getAsObject(FacesContext context, UIComponent component, String submittedValue) {
		if (submittedValue == null || submittedValue.isEmpty()) {
			return null;
		}

		try {
            return authorService.find(Integer.parseInt(submittedValue));
		} catch (NumberFormatException e) {
			throw new ConverterException(new FacesMessage(String.format("%s is not a valid User ID", submittedValue)), e);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
		if (modelValue == null) {
			return "";
		}

		if (modelValue instanceof Author) {
			return ((Author) modelValue).getAuthorId() + "";
		} else {
			throw new ConverterException(new FacesMessage(String.format("%s is not a valid User", modelValue)));
		}
	}
}
