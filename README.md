### ReadMe

NUS-HCI Summer Bootcamp of Future Interaction for Huawei Smart Glasses

We are <EASY-X> (Easy to exercise)

Here is the video link

[NUS-HCI Summer Bootcamp of Future Interaction for Smart Glasses < EASY-X >参赛视频_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1iG411x7Cs?share_medium=android&share_plat=android&share_source=QQ&share_tag=s_i×tamp=1656764780&unique_k=BLFgHlM)

### How to build

Just run the project in Android Studio

#### Compile & develop environment

- compileSdkVersion 33
- minSdkVersion 29
- targetSdkVersion 33
- Android device: Pixel 2 XL API 33 (Actually any device with version 33 is ok)

### Product Abstract

With the prevalence of online fitness classes for working out at home, users find it inconvenient to control video progress by swiping the screen while engaging in complicated training program such as yoga.

To solve this problem, we provide a multimodal approach, EasyX, which allows users to simultaneously control the video progress while keeping their hands busy. Integrated with Huawei eyewear glasses and automatic speech recognition, EasyX creates a comfortable viewing experience with effortless video progress control through voice, touch and head motion. Moreover, users can mark and jump swiftly to specific part of the clip they are interested in, so that when they wish to backtrack, they can easily do so through the mark.

To support our claims, we created a customer persona and drew up a user journey map. The result shows that the problems we identify above truly exist and our solution is effective and feasible.

### How to use this App

Users should first build APK in Android Studio and install it. In App, Users can switch between different views by clicking bottoms . And you can say some instructions to control the video progress when playing the video, here are some instructions.

- stop: to stop the vedio
- continue: continue to play vedio after stopping the vedio
- jump: jump to some certain point（special case）
- resume: replay the vedio
- mark: mark the records
- arrive: jump to the marked point
- last: jump to the last move
- next：jump to the next move
- again：repeat the move
- over：jump to the end
- forward：move forward 10s
- back：move back 10s

### Contribution

- Fcr：主要负责视频播放器
- Zxy：主要负责语音处理
- Hxl：主要负责进度条控制
- UI 由三位共同完成