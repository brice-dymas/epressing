(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('CarteBancaireDialogController', CarteBancaireDialogController);

    CarteBancaireDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CarteBancaire', 'Utilisateur'];

    function CarteBancaireDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CarteBancaire, Utilisateur) {
        var vm = this;

        vm.carteBancaire = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.utilisateurs = Utilisateur.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.carteBancaire.id !== null) {
                CarteBancaire.update(vm.carteBancaire, onSaveSuccess, onSaveError);
            } else {
                CarteBancaire.save(vm.carteBancaire, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('epressingApp:carteBancaireUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateExpiration = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
