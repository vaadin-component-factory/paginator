package com.vaadin.flow.component.incubator.vaadincom;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jfairy.Fairy;
import org.jfairy.producer.person.Person;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.incubator.Paginator;
import com.vaadin.flow.demo.DemoView;
import com.vaadin.flow.router.Route;

@Route("vaadin-incubator-paginator")
public class PaginatorView extends DemoView {

    private static final Fairy fairy = Fairy.create();

    @Override
    protected void initView() {

        H3 h3 = new H3("");

        Paginator paginator = new Paginator(10, 5, "beginning", "end");
        paginator.addChangeSelectedPageListener(event -> {
            h3.setText("Page: " + event.getPage());
        });

        h3.setText("Page: " + paginator.getCurrentPage());

        addCard("Basic Paginator", h3, paginator);

        Button b;

        createExamplePaginatorGrid();
    }


    private void createExamplePaginatorGrid(){
        Grid<Person> grid = new Grid<>();

        grid.addColumn(Person::firstName);
        grid.addColumn(Person::lastName);

        List<Person> people = generatePeople(100);

        Paginator gridPaginator = new Paginator();

        int numberItems = people.size();
        int itemsPerPage = 10;
        int numberPages = numberItems / itemsPerPage;

        gridPaginator.setPagesNumber(numberPages);

        gridPaginator.addChangeSelectedPageListener(event -> {
            loadItems(people,grid,event.getPage(),itemsPerPage);
        });

        loadItems(people,grid,1,itemsPerPage);
        addCard("Example Paginator & grid",grid,gridPaginator);

    }

    private List<Person> generatePeople(int n){
        List<Person> people = new ArrayList<>();
        for ( int i = 0; i < n; i++){
            people.add(fairy.person());
        }

        Comparator<Person> comparator = (p1,p2) -> {
            String s1 = p1.firstName() + " " + p1.lastName();
            String s2 = p2.firstName() + " " + p2.lastName();

            return s1.compareTo(s2);
        };

        return people.stream().sorted(comparator).collect(Collectors.toList());
    }


    private <T> void loadItems(List<T> data, Grid<T> grid, int page, int itemsPerPage){
        int from = (page-1) * itemsPerPage;

        int to = (from + itemsPerPage);
        to = to > data.size() ? data.size() : to;

        grid.setItems( data.subList(from,to));
    }
}
