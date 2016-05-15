# IconFont
自定义IconFont实现，支持在xml中编辑所见所得，像用png那样用IconFont

Android在appcompat的23.2.0版本以后支持了vector 适量图片的显示，最小api支持到API-7
加入部分显示效果
Android在appcompat的23.2.0版本以后支持了vector 适量图片的显示，最小api支持到API-7，下面是使用步骤

1、引用 appcompat-v7:23.2.0
dependencies {
    compile 'com.android.support:appcompat-v7:23.2.0'
}
2、关闭vector 输出 png 的功能（不添加此行会导致在android5.0以下是以png输出显示的）
android{
    defaultConfig{
        vectorDrawables.useSupportLibrary = true
    }
}
3、右键单击res/drawable —> new —> Vector Asset 出现下列弹框

4、点击 Next 生成xml文件，点击Preview可以预览

5、在ImageView ImageButton等需要用到的地方加入下面代码
xmlns:app="http://schemas.android.com/apk/res-auto"

<ImageView
    android:layout_width="100dp"
    android:layout_height="100dp"
    app:srcCompat="@drawable/service_o_vector"/>

6、然后就可以预览和打包输出了
