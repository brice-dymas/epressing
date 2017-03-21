(function() {
    'use strict';

    angular
        .module('epressingApp')
        .factory('ProduitSearch', ProduitSearch);

    ProduitSearch.$inject = ['$resource'];

    function ProduitSearch($resource) {
        var resourceUrl =  'api/_search/produits/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
