(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('CaracteristiqueDetailController', CaracteristiqueDetailController);

    CaracteristiqueDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Caracteristique'];

    function CaracteristiqueDetailController($scope, $rootScope, $stateParams, previousState, entity, Caracteristique) {
        var vm = this;

        vm.caracteristique = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('epressingApp:caracteristiqueUpdate', function(event, result) {
            vm.caracteristique = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
