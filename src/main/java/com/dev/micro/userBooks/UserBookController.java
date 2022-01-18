package com.dev.micro.userBooks;

import com.dev.micro.book.Book;
import com.dev.micro.book.BookRepository;
import com.dev.micro.user.BooksByUser;
import com.dev.micro.user.BooksByUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class UserBookController {
    @Autowired
    private  BookRepository bookRepository;
    @Autowired
    private UserBooksRepository userBooksRepository;
    @Autowired
    private BooksByUserRepository booksByUserRepository;

    @PostMapping("/addUserBook")
    public ModelAndView addBooks(@AuthenticationPrincipal OAuth2User principle,
                           @RequestBody MultiValueMap<String,String> formData){
        if (principle.equals(null) && principle.getAttribute("login").equals(null)){
            return null;
        }

        String bookId = formData.getFirst("bookId");
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (!optionalBook.isPresent()){
           return new ModelAndView("redirect:/");
        }
        Book book = optionalBook.get();

        UserBooks userBooks = new UserBooks();
        UserBooksPrimaryKey key = new UserBooksPrimaryKey();
        String userId = principle.getAttribute("login");
        key.setUserId(userId);
        key.setBookId(bookId);
        userBooks.setKey(key);

        Integer reting =  Integer.parseInt(formData.getFirst("rating"));
        userBooks.setRating(reting);
        userBooks.setStartedDate(LocalDate.parse(formData.getFirst("startDate")));
        userBooks.setCompletedDate(LocalDate.parse(formData.getFirst("completedDate")));
        userBooks.setReadingStatus(formData.getFirst("readingStatus"));
        userBooksRepository.save(userBooks);

        BooksByUser booksByUser = new BooksByUser();
        booksByUser.setId(userId);
        booksByUser.setBookId(bookId);
        booksByUser.setBookName(book.getBookNme());
        booksByUser.setAuthorNames(book.getAuthorsName());
        booksByUser.setCoverIds(book.getCoverIds());
        booksByUser.setRating(reting);
        booksByUser.setReadingStatus(formData.getFirst("readingStatus"));
        booksByUserRepository.save(booksByUser);

        return new ModelAndView("redirect:/getbook/" + bookId);
    }

}
