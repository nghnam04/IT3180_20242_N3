package vn.edu.hust.nmcnpm_20242_n3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hust.nmcnpm_20242_n3.entity.BookRequest;
import vn.edu.hust.nmcnpm_20242_n3.service.BookRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/book-requests")
public class BookRequestController {

    private final BookRequestService bookRequestService;

    @Autowired
    public BookRequestController(BookRequestService bookRequestService) {
        this.bookRequestService = bookRequestService;
    }

    @GetMapping
    public List<BookRequest> getAllRequests() {
        return bookRequestService.getAllRequests();
    }

    @PostMapping("/process/{requestId}/{approve}")
    public ResponseEntity<?> processRequest(@PathVariable String requestId, @PathVariable boolean approve) {
        BookRequest updatedRequest = bookRequestService.processRequest(requestId, approve);
        String message = approve ? "Request approved successfully" : "Request rejected successfully";
        return ResponseEntity.ok().body(new ResponseMessage(updatedRequest, message));
    }

    static class ResponseMessage {
        private BookRequest request;
        private String message;

        public ResponseMessage(BookRequest request, String message) {
            this.request = request;
            this.message = message;
        }

        public BookRequest getRequest() { return request; }
        public String getMessage() { return message; }
    }
}