package com.vaadin.flow.component.incubator;

/*
 * #%L
 * Vaadin IncubatorPaginator for Vaadin 10
 * %%
 * Copyright (C) 2017 - 2018 Vaadin Ltd
 * %%
 * This program is available under Commercial Vaadin Add-On License 3.0
 * (CVALv3).
 *
 * See the file license.html distributed with this software for more
 * information about licensing.
 *
 * You should have received a copy of the CVALv3 along with this program.
 * If not, see <http://vaadin.com/license/cval-3>.
 * #L%
 */

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Synchronize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;


/**
 * Server-side component for the <code>incubator-paginator</code> element.
 *
 * @author Vaadin Ltd
 */
@Tag("incubator-paginator")
@HtmlImport("bower_components/incubator-paginator/src/incubator-paginator.html")
public class Paginator extends PolymerTemplate<Paginator.PaginatorModel> implements HasSize {

    /**
     * Default constructor. Creates a Paginator.
     */
    public Paginator() {
        getElement().synchronizeProperty("currentPage","incubator-paginator-page-change");
        setNumberOfPages(1);
    }

    /**
     * Creates an Paginator with a specific number of pages from 1 to pagesNumber.
     * It sets the current page to the first one.
     *
     * @param pagesNumber number of pages
     */
    public Paginator(int pagesNumber) {
        this(pagesNumber, 1);
    }

    /**
     * Creates a Paginator with a specific number of pages from 1 to pagesNumber and
     * sets the current page of the pagination.
     *
     * @param pagesNumber number of pages
     * @param currentPage current page
     * @see #setNumberOfPages(int)
     * @see #setCurrentPage(int)
     */
    public Paginator(int pagesNumber, int currentPage) {
        setNumberOfPages(pagesNumber);
        setCurrentPage(currentPage);
    }

    /**
     * Creates a Paginator with a specific number of pages from 1 to pagesNumber and
     * sets the current page of the pagination. In addition, it sets the firstLabel and the lastLabel
     * of the paginator.
     *
     * @param pagesNumber number of pages
     * @param currentPage current page
     * @param firstLabel  name of the first label
     * @param lastLabel   name of the last label
     * @see #setFirstLabel(String)
     * @see #setLastLabel(String)
     */
    public Paginator(int pagesNumber, int currentPage, String firstLabel, String lastLabel) {
        this(pagesNumber, currentPage);
        setFirstLabel(firstLabel);
        setLastLabel(lastLabel);
    }


    /**
     * Creates a Paginator with a specific number of pages from 1 to pagesNumber and
     * sets the current page to the first one. In addition, it sets the firstLabel and the lastLabel
     * of the paginator.
     *
     * @param pagesNumber number of pages
     * @param firstLabel  name of the first label
     * @param lastLabel   name of the last label
     */
    public Paginator(int pagesNumber, String firstLabel, String lastLabel) {
        this(pagesNumber, 1, firstLabel, lastLabel);
    }

    /**
     * Sets the number of pages of the paginator. It has to be greater than 0.
     *
     * @param number number of pages
     */
    public void setNumberOfPages(int number) {
        if (number < 1) {
            throw new IllegalArgumentException("The number of pages has to be greeter than 0.");
        }
        getModel().setTotalPages(number);
    }

    /**
     * Sets the number of pages of the paginator. It has to be greater than 0.
     * It reloads or not the current page to the first page.
     *
     * @param number number of pages
     * @param navigateFirstPage <code>true</code> navigate to first page
     * <code>false</code>, otherwise
     */
    public void setNumberOfPages(int number, boolean navigateFirstPage) {
        setNumberOfPages(number);
        if (navigateFirstPage) {
            setFirstPage();
        }
    }

    /**
     * Gets the number of pages of the paginator.
     *
     * @return pagesNumber number of pages
     */
    public int getNumberOfPages() {
        return getModel().getTotalPages();
    }

    /**
     * Sets the current page of the paginator. The page has to be in the paginator's boundaries.
     *
     * @param page new current page
     */
    public void setCurrentPage(int page) {
        if (!isPageInBoundaries(page)) {
            throw new IllegalArgumentException("The current page (" + page + ") is outside the boundaries.");
        }
        getModel().setCurrentPage(page);
    }

    /**
     * Gets the current page of the paginator.
     *
     * @return page current page
     */
    @Synchronize("incubator-paginator-page-change")
    public int getCurrentPage() {
        return getModel().getCurrentPage();
    }


    /**
     * Sets the first page as the current page.
     */
    public void setFirstPage() {
        setCurrentPage(1);
    }

    /**
     * Sets the last page as the current page.
     */
    public void setLastPage() {
        setCurrentPage(getNumberOfPages());
    }


    /**
     * Sets the text of the first label.
     *
     * @param name first label
     */
    public void setFirstLabel(String name) {
        getModel().setFirstLabel(name);
    }

    /**
     * Gets the text of the first label.
     *
     * @return name first label
     */
    public String getFirstLabel() {
        return getModel().getFirstLabel();
    }

    /**
     * Sets the text of the last label.
     *
     * @param name last label
     */
    public void setLastLabel(String name) {
        getModel().setLastLabel(name);
    }

    /**
     * Gets the text of the last label.
     *
     * @return name last label
     */
    public String getLastLabel() {
        return getModel().getLastLabel();
    }

    /**
     * Checks if a page is in the paginator's boundaries.
     *
     * @param page page to check
     * @return <code>true</code> if the page is in the paginator's boundaries
     * <code>false</code>, otherwise
     */
    public boolean isPageInBoundaries(int page) {
        return (page > 0) && (page <= getNumberOfPages());
    }

    /**
     * Paginator page change Event is created when the user clicks on a paginator's page.
     */
    @DomEvent("incubator-paginator-page-change")
    public static class ChangeSelectedPage extends ComponentEvent<Paginator> {
        private int page;

        public ChangeSelectedPage(Paginator source, boolean fromClient, @EventData("event.detail.page") int page) {
            super(source, fromClient);
            this.page = page;
        }

        public int getPage() {
            return page;
        }
    }

    /**
     * Adds a listener for {@code ChangeSelectedPage} events fired by the webcomponent.
     *
     * @param listener the listener
     * @return a {@link Registration} for removing the event listener
     */
    public Registration addChangeSelectedPageListener(ComponentEventListener<ChangeSelectedPage> listener) {
        return addListener(ChangeSelectedPage.class, listener);
    }


    /**
     * This model binds properties between Paginator and incubator-paginator.html
     */
    public interface PaginatorModel extends TemplateModel {
        void setTotalPages(int number);

        int getTotalPages();

        void setFirstLabel(String firstLabel);

        String getFirstLabel();

        void setLastLabel(String LastLabel);

        String getLastLabel();

        void setCurrentPage(int page);

        int getCurrentPage();
    }
}
