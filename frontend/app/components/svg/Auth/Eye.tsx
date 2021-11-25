import React from "react";

const Icon: React.FC<IconC> = (props) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      xmlnsXlink="http://www.w3.org/1999/xlink"
      width="22"
      height="14"
      fill="none"
      viewBox="0 0 22 14"
      {...props}
    >
      <path fill="url(#pattern5)" d="M0 0H22V14H0z"></path>
      <defs>
        <pattern
          id="pattern5"
          width="1"
          height="1"
          patternContentUnits="objectBoundingBox"
        >
          <use transform="scale(.04545 .07143)" xlinkHref="#image5"></use>
        </pattern>
        <image
          id="image5"
          width="22"
          height="14"
          xlinkHref="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABYAAAAOCAYAAAArMezNAAABlElEQVQ4jZXUP0iVYRQG8J+SS0aguBUFDUGtgUtO1VLNDlEtUTZFRUsFBjfDaoikJksaoigQ3KrB/iw6JIibbU0FDWEZ6VJUvPFcuH59iR44cHnOOc8957zn+doajYZVbAN2YCs2B/uGD3iPn6sV1tlGXMB5dP0n5wtu4xaW10K8Dw+xJQUP8By/Em/HIRzBVZzGcbxpJWmvkF7CZEjvYTvmMIaJ+Fiwbbif3JeprSUewTAW01Gzk7vowON4R7ASG8Dh1AxnNSuIy0hn8RF78QKdGMJ39OJYvDfYUHLKmvpSey5cf4m7MYjP2I93+bM9KRzHfMtk88E6k9PEDoSjcHVXd9xWfwBrst/VHS9krJ48wu7EZrGE/hZMfvcnNhtsF16Fo3AtNDu+gjt54ak83lLG2oQZPIrPBBtMzkFMp3YkXCvuuDzeJ1zDs5zSZfxIF0eT9xVn8BSjuYxy4xdxs0lWFch1vI1ATuUKnuBkjUAKSVFouYZ/BFKnvNfY2SLpE/GqFUnfWI+ki5XEMn6ZYP0fIfwBGaRmIvE/bzAAAAAASUVORK5CYII="
        ></image>
      </defs>
    </svg>
  );
}

export default Icon;
