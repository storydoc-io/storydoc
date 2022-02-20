export function openFullscreen(document: any) {
  let documentElement = document.documentElement
  if (documentElement.requestFullscreen) {
    documentElement.requestFullscreen();
  } else if (documentElement.mozRequestFullScreen) {
    /* Firefox */
    documentElement.mozRequestFullScreen();
  } else if (documentElement.webkitRequestFullscreen) {
    /* Chrome, Safari and Opera */
    documentElement.webkitRequestFullscreen();
  } else if (documentElement.msRequestFullscreen) {
    /* IE/Edge */
    documentElement.msRequestFullscreen();
  }
}

export function closeFullscreen(document: any) {
  if (document.exitFullscreen) {
    document.exitFullscreen();
  } else if (document.mozCancelFullScreen) {
    /* Firefox */
    document.mozCancelFullScreen();
  } else if (document.webkitExitFullscreen) {
    /* Chrome, Safari and Opera */
    document.webkitExitFullscreen();
  } else if (document.msExitFullscreen) {
    /* IE/Edge */
    document.msExitFullscreen();
  }
}
