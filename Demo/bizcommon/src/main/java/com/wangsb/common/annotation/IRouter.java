package com.wangsb.common.annotation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public interface IRouter {

    IRouter build(String path);

    IRouter with(Bundle bundle);

    void go(Activity context);
}
