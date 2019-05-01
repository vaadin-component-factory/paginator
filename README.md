#Paginator

Paginator is a UI component add-on for Vaadin 10.

[Live Demo ↗](https://incubator.app.fi/incubator-breadcrumb-demo)

[<img src="https://raw.githubusercontent.com/vaadin/incubator-paginator/master/screenshot.png" width="200" alt="Screenshot of incubator-paginator">](https://vaadin.com/directory/component/vaadinincubator-paginator)

[<img src="https://raw.githubusercontent.com/vaadin/incubator-paginator-flow/master/example.png" width="400" alt="Screenshot of incubator-paginator">](https://vaadin.com/directory/component/vaadinincubator-paginator)

# What does the component do?

Paginator is a Web Component providing an easy way to display a full functioning paginator on a webpage.

# How is it used?

A simple use of the paginator component would be the following.
```java
// 10 pages, current page 1
Paginator paginator = new Paginator(10, 1, "beginning", "end");

paginator.addChangeSelectedPageListener(event -> {
    h3.setText("Page: " + event.getPage());
});
```

## With a grid
The content of the grid can be displayed in a paginated way using the paginator.
This requires some basic configuration of the grid and the paginator.

```java
Grid<Person> grid = new Grid<>();

grid.addColumn(Person::firstName);
grid.addColumn(Person::lastName);

List<Person> people = generatePeople(100);

Paginator gridPaginator = new Paginator();

int numberItems = people.size();
int itemsPerPage = 10;
int numberPages = numberItems / itemsPerPage;

gridPaginator.setNumberOfPages(numberPages);

gridPaginator.addChangeSelectedPageListener(event -> {
    loadItems(people,grid,event.getPage(),itemsPerPage);
});

loadItems(people,grid,1,itemsPerPage);
add(grid,gridPaginator);
```


# How to run the demo?

The Demo can be run going to the project incubator-paginator-flow-vaadincom-demo and executing the maven goal:

```mvn jetty:run```


## License & Author

This Add-on is distributed under [Commercial Vaadin Add-on License version 3](http://vaadin.com/license/cval-3) (CVALv3). For license terms, see LICENSE.txt.

Paginator is written by Vaadin Ltd.


## Setting up for development:

Clone the project in GitHub (or fork it if you plan on contributing)

```
git clone git@github.com/vaadin/incubator-paginator-flow.git
```

To build and install the project into the local repository run 

```mvn install -DskipITs```

in the root directory. `-DskipITs` will skip the integration tests, which require a TestBench license. If you want to run all tests as part of the build, run

```mvn install```