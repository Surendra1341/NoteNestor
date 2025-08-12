package com.notes.notenestor.serviceImpl;

import com.notes.notenestor.dto.EmailRequest;
import com.notes.notenestor.entity.Todo;
import com.notes.notenestor.entity.User;
import com.notes.notenestor.enums.TodoStatus;
import com.notes.notenestor.repository.TodoRepo;
import com.notes.notenestor.repository.UserRepo;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class TodoNotificationService {

    // help by copilot for efficient

    @Autowired
    private TodoRepo todoRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmailService emailService;


      //Runs every day at 8 AM. ->0 0 8 * * ?

    // 1 min ->  testing ->0 * * * * ?


    @Scheduled(cron = "0 0 8 * * ?")
    public void notifyIncompleteTodos() {
        // Find all todos that are NOT_COMPLETED
        List<Todo> incompleteTodos = todoRepo.findByStatusIdIn(List.of(
                TodoStatus.NOT_STARTED.getId(),
                TodoStatus.IN_PROGRESS.getId()
        ));

        // Group by user and send reminders
        incompleteTodos.stream()
                .collect(java.util.stream.Collectors.groupingBy(Todo::getCreatedBy))
                .forEach((userId, todos) -> {
                    User user = userRepo.findById(userId).orElse(null);
                    if (user != null && user.getEmail() != null && !todos.isEmpty()) {
                        StringBuilder todoListHtml = new StringBuilder();
                        for (Todo todo : todos) {
                            todoListHtml.append("<li><b>").append(todo.getTitle()).append("</b></li>");
                        }

                        String message = "<!DOCTYPE html>"
                                + "<html>"
                                + "<head>"
                                + "<meta charset='UTF-8'>"
                                + "<style>"
                                + "body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin:0; padding:0; }"
                                + ".container { max-width: 600px; margin: auto; background: #ffffff; padding: 20px; border-radius: 8px; }"
                                + ".header { text-align: center; padding-bottom: 20px; border-bottom: 1px solid #eee; }"
                                + ".header h1 { color: #2196f3; margin:0; }"
                                + ".content { padding: 20px; font-size: 16px; color: #333333; line-height: 1.5; }"
                                + ".footer { margin-top: 30px; font-size: 12px; color: #777777; text-align: center; }"
                                + ".list { margin: 10px 0; }"
                                + "</style>"
                                + "</head>"
                                + "<body>"
                                + "<div class='container'>"
                                + "<div class='header'>"
                                + "<h1>Incomplete Todo Reminder</h1>"
                                + "</div>"
                                + "<div class='content'>"
                                + "<p>Hi <b>" + user.getName() + "</b>,</p>"
                                + "<p>You have the following incomplete tasks in your NoteNestor account:</p>"
                                + "<ul class='list'>" + todoListHtml.toString() + "</ul>"
                                + "<p>Please complete them as soon as possible.</p>"
                                + "</div>"
                                + "<div class='footer'>"
                                + "<p>Thanks,<br><b>The NoteNestor Team</b></p>"
                                + "</div>"
                                + "</div>"
                                + "</body>"
                                + "</html>";

                        EmailRequest emailRequest = EmailRequest.builder()
                                .to(user.getEmail())
                                .subject("Todo Reminder: Incomplete Tasks")
                                .title("NoteNestor Team")
                                .message(message)
                                .build();

                        try {
                            emailService.sendEmail(emailRequest);
                        } catch (MessagingException | UnsupportedEncodingException e) {
                            // Log error
                            e.printStackTrace();
                        }
                    }
                });
    }
}
