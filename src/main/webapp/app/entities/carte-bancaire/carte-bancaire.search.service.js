(function() {
    'use strict';

    angular
        .module('epressingApp')
        .factory('CarteBancaireSearch', CarteBancaireSearch);

    CarteBancaireSearch.$inject = ['$resource'];

    function CarteBancaireSearch($resource) {
        var resourceUrl =  'api/_search/carte-bancaires/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
