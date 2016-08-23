# CannyViewAnimator
CannyViewAnimator is an enhanced version of ViewAnimator. It allows to use  Animators and
Transitions to extend Visibility. The logic is taken from ViewAnimator of the Android SDK.
ViewAnimator allows only one child to be visible at a time. Setting another child to be visible
causes the previous child to become invisible. This switching occurs with animation.

## Internal organization
CannyViewAnimator is divided into three layers of abstraction:
* ViewAnimator - switches the visibility of child;
* TransitionViewAnimator (extends ViewAnimator) - performs Transitions that extend Visibility;
* CannyViewAnimator (extends TransitionViewAnimator) - performs Animators;
* CannyTransition - interface for setting Transition for individual children;
* InAnimator - interface for setting animator for appearing;
* OutAnimator - interface for setting animator for disappearing.

## ViewAnimator
Includes the following public methods:
* setDisplayedChildIndex (int inChildIndex) - shows a child by its index in the parent;
* setDisplayedChildId (@IdRes int id) - shows a child by its resID;
* setDisplayedChild (View view) - shows a child by its object;
* getDisplayedChildIndex () - obtains the index of the currently displayed child;
* getDisplayedChild () - obtains the object of the currently displayed child;
* getDisplayedChildId () – gets the resID of the currently displayed child;
* bringChildToPosition (View child, int position) – changes the index of a child within the parent.

## TransitionViewAnimator
Includes the following public methods:
* startTransition() - launches Transition (starts by default after visibility changes)
* setCannyTransition (CannyTransition cannyTransition) – interface setter for setting Transition for individual children
```java
animator.setCannyTransition(new CannyTransition() {
            @Override
            public Transition getTransition(View inChild, View outChild) {
                TransitionSet transitionSet = new TransitionSet();
                transitionSet.addTransition(new Explode().addTarget(inChild));
                transitionSet.addTransition(new Fade(Fade.OUT).addTarget(outChild));
                return transitionSet;
            }
        });
```
## CannyViewAnimator
Includes the following public methods:
* setInAnimator (T ... inAnimators) – interface setter for fading in animation (using the parameters)
* setInAnimator (List<? extends inAnimators) – interface setter for fading in animation (using the list)
* setOutAnimator (T ... outAnimators) – interface setter for fading out animation (using the parameters)
* setOutAnimator (List<? extends OutAnimator>outAnimators) – interface setter for fading out animation (using the list)


```java
 animator.setInAnimator(new InAnimator() {
            @Override
            public Animator getInAnimator(View inChild, View outChild) {
                return ObjectAnimator.ofFloat(inChild, View.ALPHA, 0, 1);
            }
        });
        animator.setOutAnimator(new OutAnimator() {
            @Override
            public Animator getOutAnimator(View inChild, View outChild) {
                return ObjectAnimator.ofFloat(outChild, View.ALPHA, 1,0);
            }
        })
```


## Flags
AnimateType - indicates the order in which the animation will run.
 - Sequentially
 - Together

`animator.setAnimateType(CannyViewAnimator.TOGETHER);`

LocationType - indicates the order in which the children will be placed in the parent.
This flag makes sure that children don’t overlap with each other during animation.
 - For position
 - In always top
 - Out always top

`animator.setLocationType(CannyViewAnimator.IN_ALWAYS_TOP);`

## DefaultCannyAnimators
This class group was originally created for testing, but in the end I decided to leave them because
they help to make simple animations effortlessly. It is divided into two types:
  * PropertyAnimators - animators of View properties. [List] (library / src / main / java / com / livetyping / library / animators / property / PropertyAnimators.java)
  * RevealAnimators - CircularReveal animators. [List] (library / src / main / java / com / livetyping / library / animators / reveal / RevealAnimators.java)

Example:
```java
 animator.setInAnimator(PropertyAnimators.ROTATION_180);
 animator.setInAnimator(PropertyAnimators.ROTATION_M180);
```

## Helper classes
This group of classes is needed for easier creation of InAnimators and OutAnimators.
 * PropertyIn
 * PropertyOut
 * RevealIn
 * RevealOut

Example of parallax effect:
```java
animator.setInAnimator(new PropertyIn(View.TRANSLATION_X, width, 0).setDuration(1000));
animator.setOutAnimator(new PropertyOut(View.TRANSLATION_X, 0, -width / 2).setDuration(1000));
animator.setLocationType(CannyViewAnimator.IN_ALWAYS_TOP);
animator.setAnimateType(CannyViewAnimator.TOGETHER);
```
## XML
Attribute list:
 * `animate_type` inmplementation `setAnimateType`
 * `location_type` inmplementation `setLocationType`
 * `in` set default in animator
 * `out` set default out animator
 * `prelolipop_in` set default in animator (without reveal animators)
 * `prelolipop_out` set default out animator (without reveal animators)

If the current Android version is lower than Lollipop, then animators will be taken from
`prelolipop_in`, if `prelolipop_in` is empty, then animators will taken from `in`.

Example:
```xml
<com.livetyping.library.CannyViewAnimator
        android:id="@+id/xml_animator"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        app:animate_type="together"
        app:location_type="out_always_top"
        app:out="circular_center"
        app:pre_lollipop_out="scale_x|scale_y|alpha">
```
## License
Copyright 2015 Danil Perevalov.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

