(function() {
    'use strict';
    angular
        .module('epressingApp')
        .factory('Produit', Produit);

    Produit.$inject = ['$resource'];

    function Produit ($resource) {
        var produitsTarifsUrl = 'api/produits/produitsTarifs';
        var resourceUrl =  'api/produits/:id';
        var operationUrl = 'api/tarifs/operation/:idOperation/produit/:idProduit';
        var produitTarifUrl = 'api/tarifs/produit/:idProduit';
        
        return $resource(resourceUrl, {}, {
            'query': { url: produitsTarifsUrl, method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'getOperationPrice': {
                url: operationUrl,
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {                        
                        data = angular.fromJson(data);
                        console.log(data);
                    }
                    return data;
                }
            }, 
            'getTarifByProductID': {
                url: produitTarifUrl,
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {                        
                        data = angular.fromJson(data);
                        console.log(data);
                    }
                    return data;
                }
            }, 
            'update': { method:'PUT' }
        });
    }
})();
