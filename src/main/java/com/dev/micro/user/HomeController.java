package com.dev.micro.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    private BooksByUserRepository booksByUserRepository;
    private final String COVER_IMAGE_ROOT = "http://covers.openlibrary.org/b/id/";
    @GetMapping("/")
    public String dashBoard(@AuthenticationPrincipal OAuth2User principle,
                            Model model){
        if (principle == null || principle.getAttribute("login") == null){
            return "index";
        }
        String userId = principle.getAttribute("login");
        Slice<BooksByUser> userBooksSlice = booksByUserRepository.findAllById(userId, CassandraPageRequest.of(0, 100));
        List<BooksByUser> content = userBooksSlice.getContent();
        content = content.stream().map(book ->{
            String coverImageUrl = "/images/no-image.png";
            if (book.getCoverIds() != null & book.getCoverIds().size() > 0) {
                coverImageUrl = COVER_IMAGE_ROOT + book.getCoverIds().get(0) + "-M.jpg";
            }
            book.setCoverUrl(coverImageUrl);
            return book;
        }).collect(Collectors.toList());
        model.addAttribute("book",content);
        return "Home";
    }
}
