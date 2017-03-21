(function() {
    'use strict';

    angular
        .module('epressingApp')
        .factory('CaracteristiqueSearch', CaracteristiqueSearch);

    CaracteristiqueSearch.$inject = ['$resource'];

    function CaracteristiqueSearch($resource) {
        var resourceUrl =  'api/_search/caracteristiques/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
