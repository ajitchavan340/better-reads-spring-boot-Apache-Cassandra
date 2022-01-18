package com.dev.micro.book;

import com.dev.micro.userBooks.UserBooks;
import com.dev.micro.userBooks.UserBooksPrimaryKey;
import com.dev.micro.userBooks.UserBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class BookController {

    private final String COVER_IMAGE_ROOT = "http://covers.openlibrary.org/b/id/";

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserBooksRepository userBooksRepository;

    @GetMapping("/getbook/{bookId}")
    public String getBook(@PathVariable String bookId, Model model,
                          @AuthenticationPrincipal OAuth2User principle){
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()){
            Book book = optionalBook.get();
            String coverImageUrl = "/images/no-image.png";
            if (book.getCoverIds() != null && book.getCoverIds().size()>0){
                coverImageUrl = COVER_IMAGE_ROOT + book.getCoverIds().get(0) +"-L.jpg" ;
            }
            model.addAttribute("coverImage",coverImageUrl);
            model.addAttribute("book",book);
            if (principle != null && principle.getAttribute("login") != null){
                String userId = principle.getAttribute("login");
                model.addAttribute("loginId",userId);
                UserBooksPrimaryKey key = new UserBooksPrimaryKey();
                key.setUserId(userId);
                key.setBookId(bookId);
                Optional<UserBooks> userBooks = userBooksRepository.findById(key);
                if (userBooks.isPresent()){
                    model.addAttribute("userBooks",userBooks.get());
                }else {
                    model.addAttribute("userBooks",new UserBooks());
                }
            }
            return "book";
        }
        return "book not found";
    }
}
