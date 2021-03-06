/* global $, JitsiMeetJS */

//const options = {
//    hosts: {
//        domain: 'meetingserver.halch.cn',
//        muc: 'conference.meetingserver.halch.cn' // FIXME: use XEP-0030
//    },
//    bosh: '//meetingserver.halch.cn/http-bind', // FIXME: use xep-0156 for that
//
//    // The name of client node advertised in XEP-0115 'c' stanza
//    clientNode: 'http://meetingserver.halch.cn'
//};

const shareSeeId = parent.vid;
const options = {
    hosts: {
        domain: 'beta.meet.jit.si',
        muc: 'conference.beta.meet.jit.si' // FIXME: use XEP-0030
    },
    bosh: '//beta.meet.jit.si/http-bind', // FIXME: use xep-0156 for that

    // The name of client node advertised in XEP-0115 'c' stanza
    clientNode: 'https://beta.meet.jit.si'
};

const confOptions = {
    openBridgeChannel: true
};

let connection = null;
let isJoined = false;
let room = null;

let localTracks = [];
const remoteTracks = {};

/**
 * Handles remote tracks
 * @param track JitsiTrack object
 */
function onRemoteTrack(track) {
	if (track.isLocal()) {
        return;
    }
    if (track.getType() === 'video') {
    	if(track.videoType !== "camera"){
    		return;
    	}
	}
    const participant = track.getParticipantId();
    
    if(participant === shareSeeId){
    	if (!remoteTracks[participant]) {
            remoteTracks[participant] = [];
        }
        const idx = remoteTracks[participant].push(track);

        track.addEventListener(
            JitsiMeetJS.events.track.TRACK_AUDIO_LEVEL_CHANGED,
            audioLevel => console.log(`Audio Level remote: ${audioLevel}`));
        track.addEventListener(
            JitsiMeetJS.events.track.TRACK_MUTE_CHANGED,
            () => console.log('remote track muted'));
        track.addEventListener(
            JitsiMeetJS.events.track.LOCAL_TRACK_STOPPED,
            () => console.log('remote track stoped'));
        track.addEventListener(JitsiMeetJS.events.track.TRACK_AUDIO_OUTPUT_CHANGED,
            deviceId =>
                console.log(
                    `track audio output device was changed to ${deviceId}`));
        const id = participant + track.getType() + idx;

        if (track.getType() === 'video') {
        	if(track.videoType === "camera"){
        		track.attach($(`#localVideoV`)[0]);
        		alert("查看视频配置成功");
        	}
        } else {
            $('body').append(
                `<audio autoplay='1' id='${participant}audio${idx}' />`);
            track.attach($(`#${id}`)[0]);
            alert("查看音频配置成功");
        }
    }else{
    	alert("不是这个id")
    }
}

function onConferenceJoined() {//通知本地用户他已成功加入会议。（没有参数）
    console.log('conference joined!');
//    isJoined = true;
//    for (let i = 0; i < localTracks.length; i++) {
//        room.addTrack(localTracks[i]);
//    }
}

/**
 *
 * @param id
 */
function onUserLeft(id) {//参与者离开会议。（参数 - id（字符串），用户（JitsiParticipant））
    console.log('user left');
//    if (!remoteTracks[id]) {
//        return;
//    }
//    const tracks = remoteTracks[id];
//
//    for (let i = 0; i < tracks.length; i++) {
//        tracks[i].detach($(`#${id}${tracks[i].getType()}`));
//    }
}

/**
 * That function is called when connection is established successfully
 */
function onConnectionSuccess() {
    room = connection.initJitsiConference('conference', confOptions);
    console.info(room.myUserId());
    console.info(room.getLocalTracks());
    console.info(room.getRole());
    console.info(room.isModerator());
    room.setDisplayName("akjs");
    room.on(JitsiMeetJS.events.conference.TRACK_ADDED, onRemoteTrack);
    room.on(JitsiMeetJS.events.conference.TRACK_REMOVED, track => {
        console.log(`track removed!!!${track}`);
    });
    room.on(
        JitsiMeetJS.events.conference.CONFERENCE_JOINED,
        onConferenceJoined);
    room.on(JitsiMeetJS.events.conference.USER_JOINED, id => {
        console.log('user join');
        remoteTracks[id] = [];
    });
    room.on(JitsiMeetJS.events.conference.USER_LEFT, onUserLeft);
    room.on(JitsiMeetJS.events.conference.TRACK_MUTE_CHANGED, track => {
        console.log(`${track.getType()} - ${track.isMuted()}`);
    });
    room.on(
        JitsiMeetJS.events.conference.DISPLAY_NAME_CHANGED,
        (userID, displayName) => console.log(`${userID} - ${displayName}`));
    room.on(
        JitsiMeetJS.events.conference.TRACK_AUDIO_LEVEL_CHANGED,
        (userID, audioLevel) => console.log(`${userID} - ${audioLevel}`));
    room.on(
        JitsiMeetJS.events.conference.PHONE_NUMBER_CHANGED,
        () => console.log(`${room.getPhoneNumber()} - ${room.getPhonePin()}`));
    room.join();
}

/**
 * This function is called when the connection fail.
 */
function onConnectionFailed() {
    console.error('Connection Failed!');
}

/**
 * This function is called when we disconnect.
 */
function disconnect() {
    console.error('disconnect!');
    connection.removeEventListener(
        JitsiMeetJS.events.connection.CONNECTION_ESTABLISHED,
        onConnectionSuccess);
    connection.removeEventListener(
        JitsiMeetJS.events.connection.CONNECTION_FAILED,
        onConnectionFailed);
    connection.removeEventListener(
        JitsiMeetJS.events.connection.CONNECTION_DISCONNECTED,
        disconnect);
}


const initOptions = {
    disableAudioLevels: true,

    // The ID of the jidesha extension for Chrome.
    desktopSharingChromeExtId: 'mbocklcggfhnbahlnepmldehdhpjfcjp',

    // Whether desktop sharing should be disabled on Chrome.
    desktopSharingChromeDisabled: false,

    // The media sources to use when using screen sharing with the Chrome
    // extension.
    desktopSharingChromeSources: [ 'screen', 'window' ],

    // Required version of Chrome extension
    desktopSharingChromeMinExtVersion: '0.1',

    // Whether desktop sharing should be disabled on Firefox.
    desktopSharingFirefoxDisabled: true
};

function init(){
	JitsiMeetJS.init(initOptions);

	connection = new JitsiMeetJS.JitsiConnection(null, null, options);

	connection.addEventListener(
	    JitsiMeetJS.events.connection.CONNECTION_ESTABLISHED,
	    onConnectionSuccess);
	connection.addEventListener(
	    JitsiMeetJS.events.connection.CONNECTION_FAILED,
	    onConnectionFailed);
	connection.addEventListener(
	    JitsiMeetJS.events.connection.CONNECTION_DISCONNECTED,
	    disconnect);

	connection.connect();
}

$(window).bind('beforeunload', unload);
$(window).bind('unload', unload);
