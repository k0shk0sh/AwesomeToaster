package toaster.awesome.com.awesometoaster;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AwesomeToaster implements OnClickListener {

    /**
     * The LOG.
     */
    private String LOG = "AwesomeToaster";
    /**
     * The Activity.
     */
    private Context activity;

    /**
     * The Inflator.
     */
    private LayoutInflater inflator;

    /**
     * The Toast holder.
     */
    private LinearLayout toastHolder;

    /**
     * The Toast image.
     */
    private ImageView toastImage;

    /**
     * The Toast message.
     */
    private TextView toastMessage;

    /**
     * The Cancel toast.
     */
    private ImageView cancelToast;

    /**
     * The Message.
     */
    private String message;

    /**
     * The Gravity.
     */
    private int gravity = Gravity.BOTTOM;

    /**
     * The Time in millisecond.
     */
    private int timeInMillisecond;

    /**
     * The Toaster length.
     */
    private ToasterLength toasterLength;

    /**
     * The Awesome toaster.
     */
    private Dialog awesomeToaster;

    /**
     * The Color.
     */
    private int color;
    /**
     * The Message type.
     */
    private MessageType messageType;

    /**
     * The SHORT.
     */
    private final int SHORT = 2000;
    /**
     * The MEDIUM.
     */
    private final int MEDIUM = 5000;
    /**
     * The LONG.
     */
    private final int LONG = 8000;
    /**
     * The Display time.
     */
    private int displayTime = SHORT;

    /**
     * The enum Toaster length.
     */
    public static enum ToasterLength {
        /**
         * two seconds
         */
        SHORT,
        /**
         * four seconds
         */
        MEDIUM,
        /**
         * eight seconds
         */
        LONG
    }

    /**
     * The enum Message type.
     */
    public static enum MessageType {
        /**
         * The SUCCESS.
         */
        SUCCESS,
        /**
         * The DANGER.
         */
        DANGER,
        /**
         * The WARNING.
         */
        WARNING,
        /**
         * The NORMAL.
         */
        NORMAL
    }

    public AwesomeToaster init(Context activity) {
        this.activity = activity;
        return this;
    }

    public AwesomeToaster messageType(MessageType messageType) {
        this.messageType = messageType;
        return this;
    }

    public AwesomeToaster toasterLength(ToasterLength toasterLength) {
        this.toasterLength = toasterLength;
        return this;
    }

    public AwesomeToaster gravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public AwesomeToaster timer(int timeInMillisecond) {
        this.timeInMillisecond = timeInMillisecond;
        return this;
    }

    public AwesomeToaster message(String message) {
        this.message = message;
        return this;
    }

    public ImageView getCancelToast() {
        return cancelToast;
    }

    View root;

    @Override
    public void onClick(View v) {
        hide();
    }

    /**
     * Build awesome toaster.
     *
     * @return the awesome toaster
     */
    public AwesomeToaster build() {
        setupMessageType(messageType);
        awesomeToaster = awesomeToaster();
        setupTimer(toasterLength);
        setIconType(messageType);
        toastMessage.setText(message);
        return this;
    }

    /**
     * Show awesome toaster.
     *
     * @return the awesome toaster
     */
    public AwesomeToaster show() {
        awesomeToaster.show();
        setupHideOut();
        return this;
    }

    /**
     * Hide awesome toaster.
     *
     * @return the awesome toaster
     */
    public AwesomeToaster hide() {
        if (awesomeToaster != null && awesomeToaster.isShowing()) {
            awesomeToaster.dismiss();
        }
        return this;
    }

    /**
     * Awesome toaster.
     *
     * @return the dialog
     */
    private Dialog awesomeToaster() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.awesome_toaster);
        toastHolder = (LinearLayout) dialog.findViewById(R.id.toastHolder);
        toastImage = (ImageView) dialog.findViewById(R.id.toastImage);
        toastMessage = (TextView) dialog.findViewById(R.id.toastMessage);
        cancelToast = (ImageView) dialog.findViewById(R.id.cancelToast);
        cancelToast.setOnClickListener(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = gravity;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawableResource(color);
        return dialog;
    }

    /**
     * Sets message type.
     *
     * @param type
     *         the type
     */
    private void setupMessageType(MessageType type) {
        if (type == null) {
            color = R.color.normal;
            return;
        }
        switch (type) {
            case DANGER:
                color = R.color.danger;
                break;
            case NORMAL:
                color = R.color.normal;
                break;
            case SUCCESS:
                color = R.color.success;
                break;
            case WARNING:
                color = R.color.warning;
                break;
        }
    }

    /**
     * Sets icon type.
     *
     * @param type
     *         the type
     */
    private void setIconType(MessageType type) {
        if (type == null) {
            toastImage.setImageResource(R.drawable.ic_action_about);
            return;
        }
        switch (type) {
            case DANGER:
                toastImage.setImageResource(R.drawable.ic_action_error);
                break;
            case NORMAL:
                toastImage.setImageResource(R.drawable.ic_action_about);
                break;
            case SUCCESS:
                toastImage.setImageResource(R.drawable.ic_action_about);
                break;
            case WARNING:
                toastImage.setImageResource(R.drawable.ic_action_warning);
                break;
        }
    }

    /**
     * Sets timer.
     *
     * @param toasterLength
     *         the toaster length
     */
    private void setupTimer(ToasterLength toasterLength) {
        if (timeInMillisecond != 0) {
            displayTime = timeInMillisecond;
            return;
        } else if (toasterLength != null) {
            if (toasterLength != null) {
                switch (toasterLength) {
                    case SHORT:
                        displayTime = SHORT;
                        break;
                    case MEDIUM:
                        displayTime = MEDIUM;
                        break;
                    case LONG:
                        displayTime = LONG;
                        break;
                }
            }
            return;
        } else {
            displayTime = SHORT;
        }
    }

    /**
     * Sets hide out.
     */
    private void setupHideOut() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hide();
            }
        }, displayTime);
    }
}
