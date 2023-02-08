initialShowSlides();

// Next/previous controls
function plusSlides(n, container) {
  let index = parseInt(container.dataset.slideindex) + n;
  container.dataset.slideindex = index;
  showSlides(index, container);
}

// Thumbnail image controls
function currentSlide(n, container) {
  showSlides(n, container);
}

function initialShowSlides() {
    let containers = document.getElementsByClassName("slideshow-container");
    for(let i = 0; i < containers.length; i++) {
        showSlides(1, containers[i]);
    }
}

function showSlides(n, container) {
  let i;
  let slides = container.getElementsByClassName("mySlides");
  let dots = container.getElementsByClassName("dot");
  if (n > slides.length) {n = 1; container.dataset.slideindex = 1;}
  if (n < 1) {n = slides.length; container.dataset.slideindex = slides.length;}
  for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";
  }
  for (i = 0; i < dots.length; i++) {
    dots[i].className = dots[i].className.replace(" active", "");
  }
  slides[n-1].style.display = "block";
  dots[n-1].className += " active";
}