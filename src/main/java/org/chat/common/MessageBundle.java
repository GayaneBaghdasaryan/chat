package org.chat.common;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.jboss.logging.Logger;

@ApplicationScoped
public class MessageBundle {

  private static final Logger LOG = Logger.getLogger(MessageBundle.class);
  private ResourceBundle resourceBundle;

  @PostConstruct
  public void init() {
    try {
      resourceBundle = ResourceBundle.getBundle("messages");
    } catch (MissingResourceException ex) {
      LOG.error("Unable to load resource bundle: ", ex);
    }
  }

  public String getMessage(String key, Object... arguments) {
    try {
      String message = resourceBundle.getString(key);
      return MessageFormat.format(message, arguments);
    } catch (MissingResourceException ex) {
      LOG.errorf(ex, "Unable to get message for key [%s]", key);
      return key;
    }
  }
}
