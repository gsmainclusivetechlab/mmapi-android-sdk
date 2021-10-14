# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#########################################################################
# Retrofit 2
#########################################################################
-keepattributes Signature
-keepclassmembernames,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

#########################################################################
# OkHttp
#########################################################################
-dontwarn okhttp3.**
-dontwarn okhttp2.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
#-dontobfuscate
-optimizations !code/allocation/variable

-keep class com.gsmaSdk.gsma.** { *; }
-keep public class gotap.com.tapglkitandroid.** { *; }
# GSON.
-keepnames class com.google.gson.** {*;}
-keepnames enum com.google.gson.** {*;}
-keepnames interface com.google.gson.** {*;}
-keep class com.google.gson.** { *; }
-keepnames class org.** {*;}
-keepnames enum org.** {*;}
-keepnames interface org.** {*;}
-keep class org.** { *; }
-keepclassmembers enum * { *; }