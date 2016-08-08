# CannyViewAnimator
CannyViewAnimator an enhanced version ViewAnimator.
It allows the use of Animators and Trasitions extend Visibility.
The logic is taken from ViewAnimator of the Android SDK. ViewAnimator allows
be visible only to one child at a time. When you set the visible another child
previous visible child to become invisible. Switching occurs with animation.

## Internal organization
CannyViewAnimator divided into three layers of abstraction:
* ViewAnimator - switches Visibility Views container
* TransitionViewAnimator (extends ViewAnimator) - performs Transition extend Visibility
* CannyViewAnimator (extands TransitionViewAnimator) - performs Animators
* CannyTransition - interface to set Transition for individual children
* InAnimator - interface to set animator for appearing
* OutAnimator - interface to set animator for disappearing

## ViewAnimator
Includes the following public methods:
* setDisplayedChildIndex (int inChildIndex) - shows the child by its index in the parent
* setDisplayedChildId (@IdRes int id) - shows the child in its resID
* setDisplayedChild (View view) - shows a child on the project
* getDisplayedChildIndex () - obtain the index of the currently displayed child
* getDisplayedChild () - obtain the object currently displayed child
* getDisplayedChildId () - getting resID currently displayed child
* bringChildToPosition (View child, int position) - the change of the index of the child within the parent

## TransitionViewAnimator
Includes the following public methods:
* startTransition () - launches Transition (default start after change visibility)
* setCannyTransition (CannyTransition cannyTransition) - set the interface to set Transition for individual children
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
* setInAnimator (T ... inAnimators) - set of interfaces for the emergence through the parameters
* setInAnimator (List inAnimators <extends InAnimator?>) - set of interfaces for the emergence through the list
* setOutAnimator (T ... outAnimators) - set of interfaces for extinction by the parameters
* setOutAnimator (List outAnimators <extends OutAnimator?>) - set of interfaces to extinction through the list


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
AnimateType - It indicates the order in which the animation will run.
 - Sequentaly
 - Together

`animator.setAnimateType(CannyViewAnimator.TOGETHER);`

LocationType - It indicates what order will have children for the duration of the animation. It is necessary that the child does not overlap with another child.
 - For position
 - In always top
 - Out always top

`animator.setLocationType(CannyViewAnimator.IN_ALWAYS_TOP);`

## DeafultCannyAnimators
This class group originally created for testing. But in the end I decided to leave them, because they help to make simple animations effortlessly.
It is divided into two types:
  * PropertyAnimators - animators of View property. [List] (library / src / main / java / com / livetyping / library / animators / property / PropertyAnimators.java)
  * RevealAnimators - CircularReveal animators. [List] (library / src / main / java / com / livetyping / library / animators / reveal / RevealAnimators.java)

Example:
```java
 animator.setInAnimator(PropertyAnimators.ROTATION_180);
 animator.setInAnimator(PropertyAnimators.ROTATION_M180);
```

## Helper classes
This group of classes needed for a simple creation InAnimators and OutAnimators.
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

if the current version of the Android is lower than Lolipop, then animators will given from `prelolipop_in`, if `prelolipop_in`  is empty, then animators will given from `in`.

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

