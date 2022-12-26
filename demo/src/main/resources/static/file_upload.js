function UploadImage(preview, file_input) {

    file_input.addEventListener('change', function() {
          while(preview.firstChild) {
            preview.removeChild(preview.firstChild);
          }

          const curFiles = file_input.files;
          if (curFiles.length === 0) {
            const image = document.createElement('img');
            image.src = "/images/empty.jpg";
            image.name = file_input.id;
            preview.appendChild(image);
          } else {
            const image = document.createElement('img');
            image.src = URL.createObjectURL(curFiles[0]);
            image.name = file_input.id;
            preview.appendChild(image);
          }
    });

    file_input.click();

}

function ClearImage(preview, file_input) {
    preview.removeChild(preview.firstChild);

    file_input.value = '';
    file_input.files = null;

    const image = document.createElement('img');
    image.src = "/images/empty.jpg";
    preview.appendChild(image);
}