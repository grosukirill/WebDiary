import React from "react";

function Icon() {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      xmlnsXlink="http://www.w3.org/1999/xlink"
      width="12"
      height="23"
      fill="none"
      viewBox="0 0 12 23"
    >
      <path fill="url(#pattern3)" d="M0 0H12V22.615H0z"></path>
      <defs>
        <pattern
          id="pattern3"
          width="1"
          height="1"
          patternContentUnits="objectBoundingBox"
        >
          <use transform="scale(.00385 .00204)" xlinkHref="#image3"></use>
        </pattern>
        <image
          id="image3"
          width="260"
          height="490"
          xlinkHref="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAQQAAAHqCAYAAAGVQQBuAAAACXBIWXMAAAsTAAALEwEAmpwYAAAF8WlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNi4wLWMwMDIgNzkuMTY0MzYwLCAyMDIwLzAyLzEzLTAxOjA3OjIyICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RFdnQ9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZUV2ZW50IyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgMjEuMSAoV2luZG93cykiIHhtcDpDcmVhdGVEYXRlPSIyMDIxLTA3LTE5VDE0OjQ2OjE4KzAzOjAwIiB4bXA6TW9kaWZ5RGF0ZT0iMjAyMS0wNy0xOVQxNDo0ODowNCswMzowMCIgeG1wOk1ldGFkYXRhRGF0ZT0iMjAyMS0wNy0xOVQxNDo0ODowNCswMzowMCIgZGM6Zm9ybWF0PSJpbWFnZS9wbmciIHBob3Rvc2hvcDpDb2xvck1vZGU9IjMiIHBob3Rvc2hvcDpJQ0NQcm9maWxlPSJzUkdCIElFQzYxOTY2LTIuMSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDoxY2Y1NDQ2ZC0xM2M2LTIwNGUtYWExMy1mMzkwNjRkMzA3OWQiIHhtcE1NOkRvY3VtZW50SUQ9ImFkb2JlOmRvY2lkOnBob3Rvc2hvcDo2MTBkN2YwOS0xYzFmLTk4NGUtOTM1Ni00MmZjOWU1MGQxZjUiIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo4MWVhZGRjZS02ZDg5LTBjNDItOWE1My05Y2Q3NmI1NjEzYzEiPiA8eG1wTU06SGlzdG9yeT4gPHJkZjpTZXE+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJjcmVhdGVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOjgxZWFkZGNlLTZkODktMGM0Mi05YTUzLTljZDc2YjU2MTNjMSIgc3RFdnQ6d2hlbj0iMjAyMS0wNy0xOVQxNDo0NjoxOCswMzowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIDIxLjEgKFdpbmRvd3MpIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDoxY2Y1NDQ2ZC0xM2M2LTIwNGUtYWExMy1mMzkwNjRkMzA3OWQiIHN0RXZ0OndoZW49IjIwMjEtMDctMTlUMTQ6NDg6MDQrMDM6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCAyMS4xIChXaW5kb3dzKSIgc3RFdnQ6Y2hhbmdlZD0iLyIvPiA8L3JkZjpTZXE+IDwveG1wTU06SGlzdG9yeT4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz4KggsKAAANaklEQVR4nO3d3VYa2xaF0Q9bXjxP7rnYi3mUoBHCb9H7VZpJdDlrrEFZFLj7/ft3B94PP3Alu/0fft3hix9+vd3bnRbwya8jH9sd+dilffym398OPnCLBfzxdd6++le3ZBF7h8G8yy55iEk8xCIOD8ettmh9OPQPMQmL2LOIPYvYs4g9i9iziD2L2LOIPYvYs4g9i9iziD2L2LOIPYvYs4g9i9iziD0XU/ceYhEupu4de/rpp46F+KxJXnoSZ+2uSy/irEn8y+G4WIjfDj7ZXcrq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"
        ></image>
      </defs>
    </svg>
  );
}

export default Icon;