(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('SocioeconomicLevelDialogController', SocioeconomicLevelDialogController);

    SocioeconomicLevelDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SocioeconomicLevel'];

    function SocioeconomicLevelDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SocioeconomicLevel) {
        var vm = this;

        vm.socioeconomicLevel = entity;
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
            if (vm.socioeconomicLevel.id !== null) {
                SocioeconomicLevel.update(vm.socioeconomicLevel, onSaveSuccess, onSaveError);
            } else {
                SocioeconomicLevel.save(vm.socioeconomicLevel, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:socioeconomicLevelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
