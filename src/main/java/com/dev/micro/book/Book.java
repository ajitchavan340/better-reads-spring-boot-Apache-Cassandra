package com.dev.micro.book;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;
import java.util.List;

@Table(value = "book_by_id")
public class Book {
    @Id @PrimaryKeyColumn(ordinal = 0,type = PrimaryKeyType.PARTITIONED)
    private String book_id;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String bookNme;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String description;

    @CassandraType(type = CassandraType.Name.DATE)
    private LocalDate publishDate;

    @CassandraType(type = CassandraType.Name.LIST,typeArguments = CassandraType.Name.TEXT)
    private List<String> coverIds;

    @CassandraType(type = CassandraType.Name.LIST,typeArguments = CassandraType.Name.TEXT)
    private List<String> authorsId;

    @CassandraType(type = CassandraType.Name.LIST,typeArguments = CassandraType.Name.TEXT)
    private List<String> authorsName;


    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getBookNme() {
        return bookNme;
    }

    public void setBookNme(String bookNme) {
        this.bookNme = bookNme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public List<String> getCoverIds() {
        return coverIds;
    }

    public void setCoverIds(List<String> coverIds) {
        this.coverIds = coverIds;
    }

    public List<String> getAuthorsId() {
        return authorsId;
    }

    public void setAuthorsId(List<String> authorsId) {
        this.authorsId = authorsId;
    }

    public List<String> getAuthorsName() {
        return authorsName;
    }

    public void setAuthorsName(List<String> authorsName) {
        this.authorsName = authorsName;
    }
}
