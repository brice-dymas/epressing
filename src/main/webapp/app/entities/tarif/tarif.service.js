(function() {
    'use strict';
    angular
        .module('epressingApp')
        .factory('Tarif', Tarif);

    Tarif.$inject = ['$resource'];

    function Tarif ($resource) {
        var resourceUrl =  'api/tarifs/:id';
        var operationUrl = 'api/tarifs/operation/:idOperation/produit/:idProduit';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
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
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
