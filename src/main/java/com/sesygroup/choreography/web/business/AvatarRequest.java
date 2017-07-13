/*
 * Copyright 2017 Software Engineering and Synthesis Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sesygroup.choreography.web.business;

/**
 *
 * @author Alexander Perucci (http://www.alexanderperucci.com/)
 *
 */
public class AvatarRequest implements java.io.Serializable {
   private static final long serialVersionUID = 8179385778854968258L;

   private Double x;
   private Double y;
   private Double height;
   private Double width;
   private Integer scaleX;
   private Integer scaleY;
   private Integer rotate;

   public AvatarRequest() {
      super();
   }

   public AvatarRequest(Double x, Double y, Double height, Double width, Integer scaleX, Integer scaleY,
         Integer rotate) {
      super();
      this.x = x;
      this.y = y;
      this.height = height;
      this.width = width;
      this.scaleX = scaleX;
      this.scaleY = scaleY;
      this.rotate = rotate;
   }

   public Double getX() {
      return x;
   }

   public void setX(Double x) {
      this.x = x;
   }

   public Double getY() {
      return y;
   }

   public void setY(Double y) {
      this.y = y;
   }

   public Double getHeight() {
      return height;
   }

   public void setHeight(Double height) {
      this.height = height;
   }

   public Double getWidth() {
      return width;
   }

   public void setWidth(Double width) {
      this.width = width;
   }

   public Integer getScaleX() {
      return scaleX;
   }

   public void setScaleX(Integer scaleX) {
      this.scaleX = scaleX;
   }

   public Integer getScaleY() {
      return scaleY;
   }

   public void setScaleY(Integer scaleY) {
      this.scaleY = scaleY;
   }

   public Integer getRotate() {
      return rotate;
   }

   public void setRotate(Integer rotate) {
      this.rotate = rotate;
   }
}
