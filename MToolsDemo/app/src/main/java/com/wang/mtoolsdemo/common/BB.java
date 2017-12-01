package com.wang.mtoolsdemo.common;

/**
 * Created by dell on 2017/10/12.
 */

public class BB {

    public static String[]  data = new String[]{
            "http://img2.fengniao.com/product/157/723/ceHjSq9Gi7rw.jpg",
            "http://img2.fengniao.com/product/157/725/ceuhOIF9Nu3gw.jpg",
            "http://img2.fengniao.com/product/157/726/ce6fdSSnNDcE.jpg",
            "http://img2.fengniao.com/product/157/728/ce5OWBfCvdUsg.jpg",
            "http://img2.fengniao.com/product/157/729/cet3Qy71akHxw.jpg",
            "http://img2.fengniao.com/product/157/731/ceQ1a6veUt14c.jpg",
            "http://img2.fengniao.com/product/157/733/cenGy9PXZGD2c.jpg",
            "http://img2.fengniao.com/product/157/735/ceJFal9LhuDcM.jpg",
            "http://img2.fengniao.com/product/157/738/cev3KNFe3yEzc.jpg",
            "http://img2.fengniao.com/product/157/741/cenKQCdeiDR.jpg",
            "http://img2.fengniao.com/product/157/743/ceh3VUyMh2mrM.jpg",
            "http://img2.fengniao.com/product/156/687/ceBxNNZKz9FM.jpg",
            "http://img2.fengniao.com/product/156/701/ce8auRkGHKvZU.jpg",
            "http://img2.fengniao.com/product/156/702/ceHN1fr8pNK7Q.jpg",
            "http://img2.fengniao.com/product/156/703/ce3yD1kV5DD3U.jpg",
            "http://img2.fengniao.com/product/156/704/cecPlsMVQjdXg.jpg",
            "http://img2.fengniao.com/product/156/705/cerq31ixNQhNk.jpg",
            "http://img2.fengniao.com/product/156/706/ce5CGTyLbenxU.jpg",
            "http://img2.fengniao.com/product/156/707/ceaaE3uUMnl8k.jpg",
            "http://img2.fengniao.com/product/156/699/ceLQ6w6UxHcIw.jpg",
    };

//    public class AlertDialog extends Dialog implements DialogInterface {
//        private AlertController mAlert;
//
//        AlertDialog(Context context, @StyleRes int themeResId, boolean createContextThemeWrapper) {
//            super(context, createContextThemeWrapper ? resolveDialogTheme(context, themeResId) : 0,
//                    createContextThemeWrapper);
//
//            mWindow.alwaysReadCloseOnTouchAttr();
//            mAlert = AlertController.create(getContext(), this, getWindow());
//        }
//
//        @Override
//        public void setTitle(CharSequence title) {
//            super.setTitle(title);
//            mAlert.setTitle(title);
//        }
//
//        public void setMessage(CharSequence message) {
//            mAlert.setMessage(message);
//        }
//
//        public static class Builder {
//            private final AlertController.AlertParams P;
//
//            public Builder(Context context, int themeResId) {
//                P = new AlertController.AlertParams(new ContextThemeWrapper(
//                        context, resolveDialogTheme(context, themeResId)));
//            }
//
//            public Builder setTitle(CharSequence title) {
//                P.mTitle = title;
//                return this;
//            }
//
//            public Builder setMessage(@StringRes int messageId) {
//                P.mMessage = P.mContext.getText(messageId);
//                return this;
//            }
//
//            public AlertDialog create() {
//                // Context has already been wrapped with the appropriate theme.
//                final AlertDialog dialog = new AlertDialog(P.mContext, 0, false);
//                P.apply(dialog.mAlert);
//                dialog.setCancelable(P.mCancelable);
//                if (P.mCancelable) {
//                    dialog.setCanceledOnTouchOutside(true);
//                }
//                dialog.setOnCancelListener(P.mOnCancelListener);
//                dialog.setOnDismissListener(P.mOnDismissListener);
//                if (P.mOnKeyListener != null) {
//                    dialog.setOnKeyListener(P.mOnKeyListener);
//                }
//                return dialog;
//            }
//
//            public AlertDialog show() {
//                final AlertDialog dialog = create();
//                dialog.show();
//                return dialog;
//            }
//        }
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            mAlert.installContent();
//        }
//
//        public void installContent() {
//            int contentView = selectContentView();
//            mWindow.setContentView(contentView);
//            setupView();
//        }
//
//        private int selectContentView() {
//            if (mButtonPanelSideLayout == 0) {
//                return mAlertDialogLayout;
//            }
//            if (mButtonPanelLayoutHint == AlertDialog.LAYOUT_HINT_SIDE) {
//                return mButtonPanelSideLayout;
//            }
//            // TODO: use layout hint side for long messages/lists
//            return mAlertDialogLayout;
//        }
//    }
}
