<%--
  Created by IntelliJ IDEA.
  User: medovoy
  Date: 10.04.2022
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>

<div id="addProductPopup" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Add product to cart</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12 col-sm-6">
                        <div class="thumbnail">
                            <img class="product-image" src="..." alt="Product image">
                        </div>
                    </div>
                    <div class="col-xs-12 col-sm-6">
                        <h4 class="name">Name</h4>
                        <div class="list-group">
                            <span class="list-group-item"> <small>Category:</small> <span class="category">?</span></span>
                            <span class="list-group-item"> <small>Producer:</small> <span class="producer">?</span></span>
                        </div>
                        <div class="list-group">
                            <span class="list-group-item"> <small>Price:</small> <span type ="number" class="count" value="1" min="1" max="10">0</span></span>
                            <span class="list-group-item"> <small>Cost:</small> <span class="cost">?</span></span>
                        </div>

                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button id = "addToCart"type="button" class="btn btn-primary">Add to cart</button>
            </div>
        </div>
    </div>
</div>

