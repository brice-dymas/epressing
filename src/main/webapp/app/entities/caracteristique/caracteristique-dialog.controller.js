(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('CaracteristiqueDialogController', CaracteristiqueDialogController);

    CaracteristiqueDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Caracteristique'];

    function CaracteristiqueDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Caracteristique) {
        var vm = this;

        vm.caracteristique = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.caracteristique.id !== null) {
                Caracteristique.update(vm.caracteristique, onSaveSuccess, onSaveError);
            } else {
                Caracteristique.save(vm.caracteristique, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('epressingApp:caracteristiqueUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
