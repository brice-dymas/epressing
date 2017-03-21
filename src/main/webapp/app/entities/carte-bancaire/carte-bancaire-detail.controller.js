(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('CarteBancaireDetailController', CarteBancaireDetailController);

    CarteBancaireDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CarteBancaire', 'Utilisateur'];

    function CarteBancaireDetailController($scope, $rootScope, $stateParams, previousState, entity, CarteBancaire, Utilisateur) {
        var vm = this;

        vm.carteBancaire = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('epressingApp:carteBancaireUpdate', function(event, result) {
            vm.carteBancaire = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
