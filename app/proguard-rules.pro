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
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#Default
#keep all public and protected methods that could be used by java reflection
-keepclassmembernames class * { public protected <methods>; }
-keepclasseswithmembernames class * { native <methods>; }
-keepclasseswithmembernames class * { public <init>(android.content.Context, android.util.AttributeSet); }
-keepclasseswithmembernames class * { public <init>(android.content.Context, android.util.AttributeSet, int); }
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable { public static final android.os.Parcelable$Creator *; }

# Serialize
-keepattributes Signature,Exception,*Annotation*,SourceFile,LineNumberTable,EnclosingMethod
-keep public class * extends java.lang.Exception
-keep class sun.misc.Unsafe {*;}
-dontnote sun.misc.Unsafe

#Gson
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

#Square Config
-dontwarn com.squareup.okhttp3.internal.huc.**
-dontnote com.squareup.**
-dontwarn com.okio.**
-dontwarn okio.**
-dontwarn okhttp3.**
-dontnote okhttp3.**
-dontwarn retrofit2.**

# Jackson
-keep @com.fasterxml.jackson.annotation.JsonIgnoreProperties class * { *; }
-keep class com.fasterxml.** { *; }
-keep class org.codehaus.** { *; }
-keepnames class com.fasterxml.jackson.** { *; }
-keepclassmembers public final enum com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility {
    public static final com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility *;
}

#Glide
-keep class com.bumptech.glide.GeneratedAppGlideModuleImpl { *; }
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule { *; }
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontnote com.bumptech.glide.**

# Google Stuff
-dontwarn com.google.firebase.**
-dontwarn com.google.errorprone.annotations.*
-dontnote com.google.**
-keep class android.support.v7.widget.SearchView { *; }

# Filter out warnings that refer to legacy Code.
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Kotlin
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-dontwarn org.jetbrains.annotations.**
-dontnote kotlin.**
-dontnote kotlinx.**
-dontwarn kotlin.internal.annotations.**
-dontwarn kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor
-dontwarn kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
-dontwarn kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters
-dontwarn kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor
-dontwarn kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyDescriptorImpl
-dontwarn kotlin.reflect.jvm.internal.impl.load.java.JavaClassFinder
-dontwarn kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil
-dontwarn kotlin.reflect.jvm.internal.impl.types.DescriptorSubstitutor
-dontwarn kotlin.reflect.jvm.internal.impl.types.DescriptorSubstitutor
-dontwarn kotlin.reflect.jvm.internal.impl.types.TypeConstructor
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# RxJava
-keep class rx.schedulers.Schedulers { public static <methods>; }
-keep class rx.schedulers.ImmediateScheduler { public <methods>; }
-keep class rx.schedulers.TestScheduler { public <methods>; }
-keep class rx.schedulers.Schedulers { public static ** test(); }
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}
-dontnote io.reactivex.**
-dontnote org.reactivestreams.**
-dontnote rx.schedulers.**
-dontnote rx.internal.**
-dontnote durdinapps.rxfirebase2.**

# Test Dependencies
-dontwarn android.test.**
-dontwarn org.junit.**